package jtstats.twentyone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Allows users to create an acocunt to multiplayer, if they do not have one already
 */
public class CreateAccountActivity extends AppCompatActivity {

    /**
     * object that will hold the user information to send to the server
     */
    final JSONObject userObject = new JSONObject();

    /**
     * new Progress Dialog to be used during loading
     */
    private ProgressDialog pDialog;

    /**
     * the textview on screen that can be edited from this file
     */
    private TextView msgResponse;

    /**
     * Tests if current username is available to take
     */
    private boolean userNameOpen = false;

    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        msgResponse =  findViewById(R.id.msgResponse3);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

    }

    /**
     * Creates the new user object to send to the server based
     * on what values the user has entered
     * @param view
     */
    public void createNewUserObject(View view){

        /**
         * Hide keyboard on button click of "Create Account"
         */
        ((InputMethodManager) getSystemService(CreateAccountActivity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        final TextView usernameText  = findViewById(R.id.editText3);
        final TextView passwordText  = findViewById(R.id.editText6);
        final TextView emailText  = findViewById(R.id.editText7);

        /**
         * Put in the desired values to a new user Object
         */
        try {
            userObject.put("username" , usernameText.getText());
            userObject.put("password", passwordText.getText());
            userObject.put("email",emailText.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        usernameIsOpenCheck(usernameText.getText().toString(), view);

    }


    /**
     * Check if username is available or not
     * @param username
     * @param view
     */
    public void usernameIsOpenCheck(String username, final View view){

        userNameOpen = false;

        // Requests to the server to provide either "Login Success" or "Incorrect Password"
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://proj309-sb-04.misc.iastate.edu:8080/player/find/" + username + "/" + "*";

        //Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Checks if the login was successful, otherwise it won't move forward
                        if (response.equals("User not Found")){
                            createNewUser(view);
                            Log.d("ss ",String.valueOf(response.equals("User not Found")));
                        }
                        else {

                            String notAuthorizedMessage = "Username is taken";
                            msgResponse.setText(notAuthorizedMessage);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String errorMessage = "Error: " + error.getMessage() + ". Server is down currently. Try again shortly";
                msgResponse.setText(errorMessage);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    /**
     * Sends the userObject to the server to make a new user
     * Gets the response and goes back to log in back on
     * a successful account creation
     * @param view
     */
    public void createNewUser(final View view) {


         showProgressDialog();


         final String url = "http://proj309-sb-04.misc.iastate.edu:8080/player/add";
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, userObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            msgResponse.setText(response.toString());
                            if (response.toString().equals("{\"Status\":\"Saved\"}")
                            ){
                                hideProgressDialog();
                                msgResponse.setText("Account created, taking you back to log in");

                                /**
                                 * This small part makes a small delay before reopening the log in screen
                                 * This was the user can see their account was created and it will
                                 * redirect them back to LogIn
                                 */
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        openLogInScreen(view);
                                    }
                                }, 3000);

                            }else{
                                msgResponse.setText("There was an error");
                                hideProgressDialog();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String errorMessage = "Error: " + error.getMessage();
                            msgResponse.setText(errorMessage + "   Could not connect to server, please wait and try again.");
                            hideProgressDialog();
                        }
                    });

            queue.add(jsonObjReq);
        }


    /**
     * Open LogInScreen
      * @param view
     */
    public void openLogInScreen(View view){
        Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
        startActivity(intent);
    }

    /**
     * Helper method to show the progress dialog
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Helper method to hide the progress dialog
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    }

