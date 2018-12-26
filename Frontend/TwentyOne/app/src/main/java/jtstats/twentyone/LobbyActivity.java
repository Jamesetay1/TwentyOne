package jtstats.twentyone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

/**
 *  The LobbyActivity contains the view of the current players in a lobby for multiplayer TwentyOne
 */
public class LobbyActivity extends Activity {

    /**
     *  Temporary TextView that has text from the server
     */
    private TextView msgResponse;

    /**
     *  TextViews containing the player's usernames in a lobby
     */
    private TextView player1;
    private TextView player2;
    private TextView player3;
    private TextView player4;

    /**
     *  The ready buttons for each player
     */
    private Button player1Ready;
    private Button player2Ready;
    private Button player3Ready;
    private Button player4Ready;


    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        msgResponse =  findViewById(R.id.msgResponse); //Tempory
        player1 =  findViewById(R.id.player_1_username);
        player2 =  findViewById(R.id.player_2_username);
        player3 =  findViewById(R.id.player_3_username);
        player4 =  findViewById(R.id.player_4_username);
        player1Ready =  findViewById(R.id.player_1_ready_button);
        player2Ready =  findViewById(R.id.player_2_ready_button);
        player3Ready =  findViewById(R.id.player_3_ready_button);
        player4Ready =  findViewById(R.id.player_4_ready_button);

        player1Ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeReadyColor(player1Ready);
            }
        });
        player2Ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeReadyColor(player2Ready);
            }
        });
        player3Ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeReadyColor(player3Ready);
            }
        });
        player4Ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeReadyColor(player4Ready);
            }
        });

        connectToLobby();
    }

    /**
     *  Changes the ready button from red to green or green to red
     *  @param button  A player ready button
     */
    public void changeReadyColor(Button button) {
        //If green, set to red
        if (button.getText().equals("Ready")) {
            button.setBackgroundColor(Color.rgb(255, 102, 102));
            button.setText("Ready Up");
        }
        //Otherwise it's red, set it to green
        else {
            button.setBackgroundColor(Color.rgb(102, 255, 102));
            button.setText("Ready");
        }
    }

    /**
     *  Opens the Multiplayer Activity if all of the players are ready
     *  @param view  The view
     */
    public void openMultiplayerScreen(View view) {

        //Checks if all of the player's backgrounds are green
        if (player1Ready.getText().equals("Ready") &&
                player2Ready.getText().equals("Ready") &&
                player3Ready.getText().equals("Ready") &&
                player4Ready.getText().equals("Ready")) {

            Intent intent = new Intent(getApplicationContext(), MultiplayerActivity.class);
            startActivity(intent);
//        }
        }
    }

    /**
     *  Retrieves the lobby information from lobby 1
     */
    protected void connectToLobby() {

        JSONObject lobbyObject = new JSONObject();
        int lobbyID = 1;

        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/lobby/find/" + lobbyID;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, lobbyObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        msgResponse.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();
                       // msgResponse.setText(errorMessage);
                    }
                });

        queue.add(jsonObjReq);
    }

}


