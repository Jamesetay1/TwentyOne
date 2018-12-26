package jtstats.twentyone;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


/**
 *  This class is for the multiplayer functionality of TwentyOne
 */
public class MultiplayerActivity extends Activity {

    private TextView player1Username, player1Card1, player1Card2, player1Card3, player1Card4, player1Card5;
    private TextView player2Username, player2Card1, player2Card2, player2Card3, player2Card4, player2Card5;
    private TextView player3Username, player3Card1, player3Card2, player3Card3, player3Card4, player3Card5;
    private TextView player4Username, player4Card1, player4Card2, player4Card3, player4Card4, player4Card5;
    private TextView                  dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5;

    private TextView turnText;
    private Button hit;
    private Button stay;
    private Button split;

    private int serverTurn;
    private int clientTurn;
    private WebSocketClient cc;

    /**
     *  When the Multiplayer View is created, this method creates all of the necessary
     *  information to be displayed to the user
     * @param savedInstanceState  The saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        player1Username = findViewById(R.id.player_1_username);
        player1Card1 =  findViewById(R.id.player_1_card_1);
        player1Card2 =  findViewById(R.id.player_1_card_2);
        player1Card3 =  findViewById(R.id.player_1_card_3);
        player1Card4 =  findViewById(R.id.player_1_card_4);
        player1Card5 =  findViewById(R.id.player_1_card_5);

        player2Username = findViewById(R.id.player_2_username);
        player2Card1 =  findViewById(R.id.player_2_card_1);
        player2Card2 =  findViewById(R.id.player_2_card_2);
        player2Card3 =  findViewById(R.id.player_2_card_3);
        player2Card4 =  findViewById(R.id.player_2_card_4);
        player2Card5 =  findViewById(R.id.player_2_card_5);

        player3Username = findViewById(R.id.player_3_username);
        player3Card1 =  findViewById(R.id.player_3_card_1);
        player3Card2 =  findViewById(R.id.player_3_card_2);
        player3Card3 =  findViewById(R.id.player_3_card_3);
        player3Card4 =  findViewById(R.id.player_3_card_4);
        player3Card5 =  findViewById(R.id.player_3_card_5);

        player4Username = findViewById(R.id.player_4_username);
        player4Card1 =  findViewById(R.id.player_4_card_1);
        player4Card2 =  findViewById(R.id.player_4_card_2);
        player4Card3 =  findViewById(R.id.player_4_card_3);
        player4Card4 =  findViewById(R.id.player_4_card_4);
        player4Card5 =  findViewById(R.id.player_4_card_5);

        dealerCard1 =  findViewById(R.id.dealer_card_1);
        dealerCard2 =  findViewById(R.id.dealer_card_2);
        dealerCard3 =  findViewById(R.id.dealer_card_3);
        dealerCard4 =  findViewById(R.id.dealer_card_4);
        dealerCard5 =  findViewById(R.id.dealer_card_5);

        turnText = findViewById(R.id.turn_text_view);
        hit = findViewById(R.id.button_hit);

//     hit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Draft[] drafts = {new Draft_6455()};
//
//                /**
//                 * If running this on an android device, make sure it is on the same network as your
//                 * computer, and change the ip address to that of your computer.
//                 * If running on the emulator, you can use localhost.
//                 */
//                String w = "http://proj309-sb-04.misc.iastate.edu:8080/chat/{username}";
//
//                try {
//                    Log.d("Socket:", "Trying socket");
//                    cc = new WebSocketClient(new URI(w),(Draft) drafts[0]) {
//                        @Override
//                        public void onMessage(String message) {
//                            Log.d("", "run() returned: " + message);
//                        }
//
//                        @Override
//                        public void onOpen(ServerHandshake handshake) {
//                            Log.d("OPEN", "run() returned: " + "is connecting");
//                        }
//
//                        @Override
//                        public void onClose(int code, String reason, boolean remote) {
//                            Log.d("CLOSE", "onClose() returned: " + reason);
//                        }
//
//                        @Override
//                        public void onError(Exception e)
//                        {
//                            Log.d("Exception:", e.toString());
//                        }
//                    };
//                }
//                catch (URISyntaxException e) {
//                    Log.d("Exception:", e.getMessage().toString());
//                    e.printStackTrace();
//                }
//                cc.connect();
//
//            }
//        });

        startGame();
    }

    /**
     *  Checks the server lobby for how many players are in the game
     */
    public void startGame() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://proj309-sb-04.misc.iastate.edu:8080/lobby/findusers/1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        initGame(response);
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        queue.add(stringRequest);
    }

    /**
     * Initializes the TwentyOne game. This method will get the number of players in the lobby
     * and delete unwanted users from the view. Then it will get each player's hand and fill
     * the empty card slots with the appropriate values.
     */
    public void initGame(String responseNumPlayers) {

        int playerCount = Integer.parseInt(responseNumPlayers);

        //Get the dealer's hand
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://proj309-sb-04.misc.iastate.edu:8080/game/find/1/dealerHand";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Integer> dealerHand = parsePlayerHand(response);
                        String card1 = "" + dealerHand.get(0);
                        String card2 = "" + dealerHand.get(1);
                        dealerCard1.setText(card1);
                        dealerCard2.setText(card2);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        queue.add(stringRequest);

        //Get player 1's hand
        queue = Volley.newRequestQueue(this);
        url ="http://proj309-sb-04.misc.iastate.edu:8080/game/find/1/playerOneHand";

        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Integer> player1Hand = parsePlayerHand(response);
                        String card1 = "" + player1Hand.get(0);
                        String card2 = "" + player1Hand.get(1);
                        player1Card1.setText(card1);
                        player1Card2.setText(card2);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        queue.add(stringRequest);

        //Get player 2's hand
        url ="http://proj309-sb-04.misc.iastate.edu:8080/game/find/1/playerTwoHand";

        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Integer> player2Hand = parsePlayerHand(response);
                        String card1 = "" + player2Hand.get(0);
                        String card2 = "" + player2Hand.get(1);
                        player2Card1.setText(card1);
                        player2Card2.setText(card2);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        queue.add(stringRequest);

        //Remove players 3 and 4 if their spots are empty
        if (playerCount == 2) {

            player3Username.setVisibility(View.INVISIBLE);
            player3Card1.setVisibility(View.INVISIBLE);
            player3Card2.setVisibility(View.INVISIBLE);
            player3Card3.setVisibility(View.INVISIBLE);
            player3Card4.setVisibility(View.INVISIBLE);
            player3Card5.setVisibility(View.INVISIBLE);

            player4Username.setVisibility(View.INVISIBLE);
            player4Card1.setVisibility(View.INVISIBLE);
            player4Card2.setVisibility(View.INVISIBLE);
            player4Card3.setVisibility(View.INVISIBLE);
            player4Card4.setVisibility(View.INVISIBLE);
            player4Card5.setVisibility(View.INVISIBLE);
        }
        //Remove player 4 if their spot is empty
        else if (playerCount == 3) {

            //Get player 3's hand
            url ="http://proj309-sb-04.misc.iastate.edu:8080/game/find/1/playerThreeHand";

            stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<Integer> player3Hand = parsePlayerHand(response);
                            String card1 = "" + player3Hand.get(0);
                            String card2 = "" + player3Hand.get(1);
                            player3Card1.setText(card1);
                            player3Card2.setText(card2);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });

            queue.add(stringRequest);

            player4Username.setVisibility(View.INVISIBLE);
            player4Card1.setVisibility(View.INVISIBLE);
            player4Card2.setVisibility(View.INVISIBLE);
            player4Card3.setVisibility(View.INVISIBLE);
            player4Card4.setVisibility(View.INVISIBLE);
            player4Card5.setVisibility(View.INVISIBLE);
        }
        //All four players are present
        else if (playerCount == 4) {

            //Get player 3's hand
            url ="http://proj309-sb-04.misc.iastate.edu:8080/game/find/1/playerThreeHand";

            stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<Integer> player3Hand = parsePlayerHand(response);
                            String card1 = "" + player3Hand.get(0);
                            String card2 = "" + player3Hand.get(1);
                            player3Card1.setText(card1);
                            player3Card2.setText(card2);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });

            queue.add(stringRequest);

            //Get player 4's hand
            url ="http://proj309-sb-04.misc.iastate.edu:8080/game/find/1/playerFourHand";

            stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<Integer> player4Hand = parsePlayerHand(response);
                            String card1 = "" + player4Hand.get(0);
                            String card2 = "" + player4Hand.get(1);
                            player4Card1.setText(card1);
                            player4Card2.setText(card2);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });

            queue.add(stringRequest);
        }

        //Set the turn text view
        getPlayerTurn();
    }

    /**
     *  Gets the current player's turn from the server and updates the text box with the correct
     *  information
     */
    public void getPlayerTurn() {

        JSONArray games = new JSONArray();

        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/game/find/all";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, games,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curGame = response.getJSONObject(i);
                                String turn  = "Player " + curGame.getString("playerTurn") + " it's your turn!";
                                if (curGame.getString("playerTurn").equals("5")) {
                                    turn = "Dealer it's your turn!";
                                }
                                turnText.setText(turn);
                            }
                        }
                        catch(final JSONException e) { Log.d("error",e.getMessage()); }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();
                        Log.d("error", errorMessage);
                    }
                });

        queue.add(jsonObjReq);
    }

    /**
     * Parses the hand of a player passed in as an argument, then returns an ArrayList
     * of card Strings
     * @param hand  the hand
     * @return  ArrayList representing the player's hand
     */
    public ArrayList<Integer> parsePlayerHand(String hand) {

        ArrayList<Integer> result = new ArrayList<>();
        String[] cards = hand.split(", ");

        for (String card : cards) {
            if (card.substring(0,2).equals("10")) {
                int cardVal = Integer.parseInt(card.substring(0,2));
                result.add(cardVal);
            }
            else {
                int cardVal = Integer.parseInt(card.substring(0,1));
                result.add(cardVal);
            }
        }

        return result;
    }

    /**
     * Gets the turn according to the server. It looks at the turn display and parses out whose
     * turn it is
     */
    public void getServerTurn() {
        String curTurn = turnText.getText().toString();

        if (curTurn.contains("1")) {
            serverTurn = 1;
        }
        else if (curTurn.contains("2")) {
            serverTurn = 2;
        }
        else if (curTurn.contains("3")) {
            serverTurn = 3;
        }
        else if (curTurn.contains("4")) {
            serverTurn = 4;
        }
    }

    /**
     *  When the "hit" button is pressed, this method is called to handle it and send
     *  the necessary information to the server
     * @param view  The view
     */
    public void handleHitButton(View view) {
        getServerTurn();
        checkWhoPressedHit();
    }

    /**
     * Checks what user pressed hit
     */
    public void checkWhoPressedHit() {

        final String username = LogInActivity.getUsername();

        JSONObject lobbyObject = new JSONObject();

        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/lobby/find/1";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, lobbyObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            String curUsername = response.getString("playerOne");
                            if (curUsername.equals(username)) {
                                getClientTurn(1);
                            }
                            curUsername = response.getString("playerTwo");
                            if (curUsername.equals(username)) {
                                getClientTurn(2);
                            }
                            curUsername = response.getString("playerThree");
                            if (curUsername.equals(username)) {
                                getClientTurn(3);
                            }
                            curUsername = response.getString("playerFour");
                            if (curUsername.equals(username)) {
                                getClientTurn(4);
                            }
                        }
                        catch(final JSONException e) { Log.d("error",e.getMessage()); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });

        queue.add(jsonObjReq);
    }

    /**
     * Stores the user that pressed hit in the clientTurn
     * @param client  The client that pressed hit
     */
    public void getClientTurn(int client) {
        clientTurn = client;
        compareHitPresses();
    }

    /**
     * Compares if the user who pressed hit can actually hit. If the server and client are
     * different, this method does nothing.
     */
    public void compareHitPresses() {

        if (clientTurn == serverTurn) {

            String stringClientTurn = "";
            if (clientTurn == 1) {
                stringClientTurn = "playerOne";
            }
            else if (clientTurn == 2) {
                stringClientTurn = "playerTwo";
            }
            else if (clientTurn == 3) {
                stringClientTurn = "playerThree";
            }
            else if (clientTurn == 4) {
                stringClientTurn = "playerFour";
            }

            JSONObject hitObject = new JSONObject();

            String url = "http://proj309-sb-04.misc.iastate.edu:8080/game/1/hit/" + stringClientTurn;
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                    url, hitObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("card", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.getMessage());
                        }
                    });

            queue.add(jsonObjReq);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { Log.d("error",e.getMessage()); }

            queue = Volley.newRequestQueue(this);
            url ="http://proj309-sb-04.misc.iastate.edu:8080/game/find/1/" + stringClientTurn + "Hand";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<Integer> playerHand = parsePlayerHand(response);
                            Log.d("hand", playerHand.toString());
                            updatePlayerHand(playerHand, clientTurn);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });

            queue.add(stringRequest);
        }
    }

    /**
     * Updates the player hands from the server and displays it on their screen. Example, if
     * Player 1 presses "Hit", this method will add a card to their hand and display it just
     * for that user.
     * @param hand  The current player's hand
     * @param client  The player that needs their hand updated
     */
    public void updatePlayerHand(ArrayList<Integer> hand, int client) {

        String card1 = "" + hand.get(0);
        String card2 = "" + hand.get(1);

        //For 2, it is a hard reset
        if (hand.size() == 2) {
            if (client == 1) {
                player1Card1.setText(card1);
                player1Card2.setText(card2);
                player1Card3.setText("+");
                player1Card4.setText("+");
                player1Card5.setText("+");
            }
            else if (client == 2) {
                player2Card1.setText(card1);
                player2Card2.setText(card2);
                player2Card3.setText("+");
                player2Card4.setText("+");
                player2Card5.setText("+");
            }
            else if (client == 3) {
                player3Card1.setText(card1);
                player3Card2.setText(card2);
                player3Card3.setText("+");
                player3Card4.setText("+");
                player3Card5.setText("+");
            }
            else if (client == 4) {
                player4Card1.setText(card1);
                player4Card2.setText(card2);
                player4Card3.setText("+");
                player4Card4.setText("+");
                player4Card5.setText("+");
            }
            else if (client == 5) {
                dealerCard1.setText(card1);
                dealerCard2.setText(card2);
                dealerCard3.setText("+");
                dealerCard4.setText("+");
                dealerCard5.setText("+");
            }
        }
        else if (hand.size() == 3) {

            String card3 = "" + hand.get(2);

            if (client == 1) {
                player1Card1.setText(card1);
                player1Card2.setText(card2);
                player1Card3.setText(card3);
            }
            else if (client == 2) {
                player2Card1.setText(card1);
                player2Card2.setText(card2);
                player2Card3.setText(card3);
            }
            else if (client == 3) {
                player3Card1.setText(card1);
                player3Card2.setText(card2);
                player3Card3.setText(card3);
            }
            else if (client == 4) {
                player4Card1.setText(card1);
                player4Card2.setText(card2);
                player4Card3.setText(card3);
            }
            else if (client == 5) {
                dealerCard1.setText(card1);
                dealerCard2.setText(card2);
                dealerCard3.setText(card3);
            }
        }
        else if (hand.size() == 4) {

            String card3 = "" + hand.get(2);
            String card4 = "" + hand.get(3);

            if (client == 1) {
                player1Card1.setText(card1);
                player1Card2.setText(card2);
                player1Card3.setText(card3);
                player1Card4.setText(card4);
            }
            else if (client == 2) {
                player2Card1.setText(card1);
                player2Card2.setText(card2);
                player2Card3.setText(card3);
                player2Card4.setText(card4);
            }
            else if (client == 3) {
                player3Card1.setText(card1);
                player3Card2.setText(card2);
                player3Card3.setText(card3);
                player3Card4.setText(card4);
            }
            else if (client == 4) {
                player4Card1.setText(card1);
                player4Card2.setText(card2);
                player4Card3.setText(card3);
                player4Card4.setText(card4);
            }
            else if (client == 5) {
                dealerCard1.setText(card1);
                dealerCard2.setText(card2);
                dealerCard3.setText(card3);
                dealerCard4.setText(card4);
            }
        }
        else if (hand.size() == 5) { //TODO: if over 5 cards or over 21, stand

            String card3 = "" + hand.get(2);
            String card4 = "" + hand.get(3);
            String card5 = "" + hand.get(4);

            if (client == 1) {
                player1Card1.setText(card1);
                player1Card2.setText(card2);
                player1Card3.setText(card3);
                player1Card4.setText(card4);
                player1Card5.setText(card5);
            }
            else if (client == 2) {
                player2Card1.setText(card1);
                player2Card2.setText(card2);
                player2Card3.setText(card3);
                player2Card4.setText(card4);
                player2Card5.setText(card5);
            }
            else if (client == 3) {
                player3Card1.setText(card1);
                player3Card2.setText(card2);
                player3Card3.setText(card3);
                player3Card4.setText(card4);
                player3Card5.setText(card5);
            }
            else if (client == 4) {
                player4Card1.setText(card1);
                player4Card2.setText(card2);
                player4Card3.setText(card3);
                player4Card4.setText(card4);
                player4Card5.setText(card5);
            }
            else if (client == 5) {
                dealerCard1.setText(card1);
                dealerCard2.setText(card2);
                dealerCard3.setText(card3);
                dealerCard4.setText(card4);
                dealerCard5.setText(card5);
            }
        }
    }

    /**
     *  When the "stay" button is pressed, this method is called to handle it and send
     *  the necessary information to the server
     * @param view  The view
     */
    public void handleStayButton(View view) {
        getServerTurn();
        checkWhoPressedStay();
    }

    /**
     * Checks what user pressed stay
     */
    public void checkWhoPressedStay() {

        final String username = LogInActivity.getUsername();
        JSONObject lobbyObject = new JSONObject();

        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/lobby/find/1";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, lobbyObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            String curUsername = response.getString("playerOne");
                            if (curUsername.equals(username)) {
                                getClientStayTurn(1);
                            }
                            curUsername = response.getString("playerTwo");
                            if (curUsername.equals(username)) {
                                getClientStayTurn(2);
                            }
                            curUsername = response.getString("playerThree");
                            if (curUsername.equals(username)) {
                                getClientStayTurn(3);
                            }
                            curUsername = response.getString("playerFour");
                            if (curUsername.equals(username)) {
                                getClientStayTurn(4);
                            }
                        }
                        catch(final JSONException e) { Log.d("error",e.getMessage()); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });

        queue.add(jsonObjReq);
    }

    /**
     * Stores the user that pressed stay in the clientTurn
     * @param client  The client that pressed stay
     */
    public void getClientStayTurn(int client) {
        clientTurn = client;
        compareStayPresses();

    }

    /**
     * Compares if the user who pressed stay can actually stay. If the server and client are
     * different, this method does nothing.
     */
    public void compareStayPresses() {

        if (clientTurn == serverTurn) {

            String stringClientTurn = "";
            if (clientTurn == 1) {
                stringClientTurn = "playerOne";
            } else if (clientTurn == 2) {
                stringClientTurn = "playerTwo";
            } else if (clientTurn == 3) {
                stringClientTurn = "playerThree";
            } else if (clientTurn == 4) {
                stringClientTurn = "playerFour";
            }

            JSONObject stayObject = new JSONObject();

            String url = "http://proj309-sb-04.misc.iastate.edu:8080/game/1/stay/" + stringClientTurn;
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                    url, stayObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {}
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.getMessage());
                        }
                    });

            queue.add(jsonObjReq);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { Log.d("error",e.getMessage()); }

            getPlayerTurn();
        }
    }

    /**
     *  When the "split" button is pressed, this method is called to handle it and send
     *  the necessary information to the server
     * @param view  The view
     */
    public void handleSplitButton(View view) {

        //TODO: Make a POST request to the server with username ____, split 1
    }

    //TODO: This is where we need to implement WebSockets into the game
    /**
     * This method is called when the refresh button is pressed
     * @param view  The view
     */
    public void updateGameStatus(final View view) {

        JSONArray games = new JSONArray();

        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/game/find/all";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, games,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curGame = response.getJSONObject(i);

                                int dealerVal = Integer.parseInt(curGame.getString("dealerValue"));
                                if (turnText.getText().toString().equals("Dealer it's your turn!") && dealerVal > 16) {
                                    endGame(view);
                                    return;
                                }

                                String p1hand = curGame.getString("playerOneHand");
                                ArrayList<Integer> player1Hand = parsePlayerHand(p1hand);
                                updatePlayerHand(player1Hand, 1);

                                String p2hand = curGame.getString("playerTwoHand");
                                ArrayList<Integer> player2Hand = parsePlayerHand(p2hand);
                                updatePlayerHand(player2Hand, 2);

                                String p3hand = curGame.getString("playerThreeHand");
                                if (!p3hand.equals("empty")) {
                                    ArrayList<Integer> player3Hand = parsePlayerHand(p3hand);
                                    updatePlayerHand(player3Hand, 3);
                                }

                                String p4hand = curGame.getString("playerFourHand");
                                if (!p4hand.equals("empty")) {
                                    ArrayList<Integer> player4Hand = parsePlayerHand(p4hand);
                                    updatePlayerHand(player4Hand, 4);
                                }

                                getPlayerTurn();
                                if (turnText.getText().toString().equals("Dealer it's your turn!")) {
                                    takeDealerTurn();
                                }

                                if (turnText.getText().toString().contains("Congratulations")) {
                                    takeDealerTurn();
                                    endGame(view);
                                }
                            }
                        }
                        catch(final JSONException e) { Log.d("error",e.getMessage()); }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();
                        Log.d("error", errorMessage);
                    }
                });

        queue.add(jsonObjReq);
    }

    /**
     * Takes the dealer's turn by grabbing information from the server and sending it back to
     * the client.
     */
    public void takeDealerTurn() {

        String url = "http://proj309-sb-04.misc.iastate.edu:8080/game/1/dealerTurn";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.getMessage());
                    }
                });

        queue.add(jsonObjReq);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) { Log.d("error",e.getMessage()); }

        queue = Volley.newRequestQueue(this);
        url ="http://proj309-sb-04.misc.iastate.edu:8080/game/find/1/dealerHand";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Integer> dealerHand = parsePlayerHand(response);
                        Log.d("msg", dealerHand.toString());
                        updatePlayerHand(dealerHand, 5);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        queue.add(stringRequest);
    }

    /**
     * Ends the game if the dealer has made their turn. Also, this will display the winner
     * @param view  The view
     */
    public void endGame(View view) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://proj309-sb-04.misc.iastate.edu:8080/game/1/winner";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String winner = "Congratulations " + response + ", you win!";
                        turnText.setText(winner);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });

        queue.add(stringRequest);


    }
}
