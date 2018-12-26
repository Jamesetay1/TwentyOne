package jtstats.twentyone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Controls only accessible to Administrators
 */
public class AdminControls extends AppCompatActivity {

    /**
     * Which lobbyID Object will be started on a call to startGame()
     */
    private JSONObject lobbyIDObject = new JSONObject();

    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controls);
    }

    /**
     * Resets Lobby 1
     * @param view
     */
    public void ResetLobbies(final View view){


        final JSONObject lobbyResetObject = new JSONObject();



        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/game/1/gameReset";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url, lobbyResetObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("",response.toString());



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();

                    }
                });

        queue.add(jsonObjReq);
    }

    /**
     * Retrieves the jsonObject that details our lobby ID object
     * @param view
     */
    public void getlobbyIDObject(final View view){

        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/lobby/find/1";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, lobbyIDObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("In getlobbyIDObject", response.toString());
                        setLobbyIDObject(response, view);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();

                    }
                });

        queue.add(jsonObjReq);


    }

    /**
     * Set the lobbyID of the game to start
     * @param obj
     * @param view
     */
    public void setLobbyIDObject(JSONObject obj, final View view){

        Log.d("in setLobbyIDObject bef", obj.toString());
        lobbyIDObject = obj;
        Log.d("in setLobbyIDObject aft", lobbyIDObject.toString());
        StartGame1(view);
    }

    /**
     * Start the game of the previously set lobby ID.
     * @param view
     */
    public void StartGame1(final View view){



        Log.d("at beg of StartGame1", lobbyIDObject.toString());
        final  JSONObject gameStartObject = lobbyIDObject;
        Log.d ("test:  ", gameStartObject.toString());
        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/game/1/startgame";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url, gameStartObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("",response.toString());



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();

                    }
                });

        queue.add(jsonObjReq);
    }

    /**
     * If the reset button gets pressed, the lobby gets reset to empty
     * @param view  view
     */
    public void resetLobby(View view) {

        JSONObject resetObject = new JSONObject();

        final String url = "http://proj309-sb-04.misc.iastate.edu:8080/lobby/resetlobby";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, resetObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();
                        Log.d("",errorMessage);
                    }
                });

        queue.add(jsonObjReq);
    }
}
