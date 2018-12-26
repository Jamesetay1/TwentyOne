package jtstats.twentyone;

import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.android.volley.toolbox.JsonObjectRequest;

/**
 * This Activity allows the user to join a lobby
 */
public class LobbyJoin extends Activity {

    /**
     * Displays the response from the server
     */
    private TextView msgResponse;

    /**
     * Admin Controls Button
     */

    private Button adminControls;
    /**
     * What we will be placing into the lobby through the server
     */
    final JSONObject logInObject = new JSONObject();

    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_join);


        adminControls = findViewById(R.id.button11);
        adminControls.setVisibility(View.GONE);
        showAdminButton();

        msgResponse =  findViewById(R.id.msgResponse2);
        getLogInObject(logInObject);
    }

    /**
     * The user can press the LogOut Button and it will take them to the home screen
     * It will also stop the user from auto log in on the Multiplayer Screen
     * @param view
     */
    public void LogOut(View view){
        LogInActivity.clearLogInInfo();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    /**
     * If is an admin, will show the button with all the administrator options.
     */
    public void showAdminButton(){
        if (LogInActivity.getUsername().equals("James")||
                LogInActivity.getUsername().equals("Keaton")||
                LogInActivity.getUsername().equals("Thomas") ||
                LogInActivity.getUsername().equals("Tyler")){
            adminControls.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Parses the logInObject returned by the server from the username.
     * Then calls createLogInObject with this new information
     * @param logInObject
     */
    public void getLogInObject(JSONObject logInObject){
        String usernameToFind = LogInActivity.getUsername();


        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/player/find/" + usernameToFind;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, logInObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //msgResponse.setText(response.toString());

                      try{
                          
                          final String id =  response.getString("id");
                          final String email = response.getString("email");
                          final String username =  response.getString("username");
                          final String password = response.getString("password");

                          Log.d("msg", id + " " + email + " " + username + " " + password);

                          createLogInObject(id, email, username, password);
                      }catch(final JSONException e) {

                        }

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

    /**
     * Crate the log in object that will be used to join a lobby.
     * @param id
     * @param email
     * @param username
     * @param password
     */
    public void createLogInObject(String id, String email, String username, String password) {
        try {

            logInObject.put("username" , username);
            logInObject.put("id", id);
            logInObject.put("email", email);
            logInObject.put("password",password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    /**
     * Attempts to connect to the lobby. Gets triggered when the user presses
     * "Join Lobby"
     * @param view  view
     */
    protected void connectToLobby(final View view) {

        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/lobby/joinlobby";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url, logInObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // msgResponse.setText(String.valueOf(response));
                        joinLobby(view);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();
                       // msgResponse.setText(errorMessage);
                        joinLobby(view);
                    }
                });

        queue.add(jsonObjReq);
    }

    /**
     * Jumps to LobbyActivity
     * @param view  view
     */
    public void joinLobby(View view){
        Intent intent = new Intent(getApplicationContext(),LobbyActivity.class);
        startActivity(intent);
    }



    /**
     * Jumps to LobbyActivity
     * @param view  view
     */
    public void openAdminControls(View view){
        Intent intent = new Intent(getApplicationContext(),AdminControls.class);
        startActivity(intent);
    }



}
