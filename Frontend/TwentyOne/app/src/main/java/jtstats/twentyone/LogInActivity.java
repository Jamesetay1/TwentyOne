package jtstats.twentyone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

/**
 * This class handles the login screen for TwentyOne. The purpose is for the user to enter their credentials,
 * then the server checks if the user and password match one found in the database, and finally
 * sends a response which will either allow the user to login or it tells them their password was incorrect
 */
public class LogInActivity extends AppCompatActivity {

    /**
     * Displays the response from attempting to login
     */
    private TextView msgResponse;

    /**
     * Displays a progress circle when user logs in
     */
    private ProgressDialog pDialog;

    /**
     * holds the username of the user logging in
     */
    private static TextView usernameText = null;

    /**
     * View for our
     */
    public View view;

    /**
     * SharedPrefrences for logging in
     */
    public SharedPreferences sp;

    /**
     * SharedPrefrences editor for logging in
     */
    public static SharedPreferences.Editor Ed;



    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        msgResponse =  findViewById(R.id.msgResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Logging in...");
        pDialog.setCancelable(false);

       sp = PreferenceManager.getDefaultSharedPreferences(this);
         Ed=sp.edit();


        if(infoIsStored() == true){
            checkCredentials(view);
        }
    }

    /**
     * Check if information is already stored by shared preferences
     * @return if there is info there or not.
     */
    public boolean infoIsStored(){

        String usn =sp.getString("User", null);
        Log.d("whatever", String.valueOf(usn!=null));
        return (usn != null);
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

    /**
     * Opens the lobby screen
     * @param view  View
     */
    public void openLobbyScreen(View view){
        Intent intent = new Intent(getApplicationContext(),LobbyJoin.class);
        startActivity(intent);
    }

    /**
     * Stores the information the user just logged in with on their device
     * @param username
     * @param password
     */
    public void storeLogInInfo(String username, String password){

        Ed.putString("User", username);
        Ed.putString("Pass", password);
        Ed.commit();
        Log.d("whatever1", sp.getString("User", null));
    }

    /**
     * When a user logs out, clear the info stored on their device.
     */
    public static void clearLogInInfo(){
        Ed.putString("User", null);
        Ed.putString("Pass", null);
        Ed.commit();
        }


    /**
     * Checks the credentials of the user's username and password.
     * If it was found, it will open the lobby screen
     * Else, it states, "Incorrect username or password"
     * @param view  View
     */
    public void checkCredentials(final View view) {

        //Hides the keyboard (if not already closed).
        ((InputMethodManager) getSystemService(LogInActivity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        showProgressDialog();

        String username, password;
        if(infoIsStored()){

            //SharedPreferences sp=this.getSharedPreferences("Login", MODE_PRIVATE);

            username=sp.getString("User", null);
            password = sp.getString("Pass", null);
            usernameText = findViewById(R.id.editText);
            usernameText.setText(username);


        }else {
            usernameText = findViewById(R.id.editText);
            username = usernameText.getText().toString();
            final TextView passwordText = findViewById(R.id.editText2);
            password = passwordText.getText().toString();
        }

        //Store Log In Info
        storeLogInInfo(username, password);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://proj309-sb-04.misc.iastate.edu:8080/player/find/" + username + "/" + password;

        //Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Checks if the login was successful, otherwise it won't move forward
                        if (response.equals("Login Success")){
                            hideProgressDialog();
                            openLobbyScreen(view);
                        }
                        else {
                            hideProgressDialog();
                            String notAuthorizedMessage = "Incorrect username or password";
                            msgResponse.setText(notAuthorizedMessage);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                String errorMessage = "Error: " + error.getMessage() + ". Server is down currently. Try again shortly";
                msgResponse.setText(errorMessage);
                openLobbyScreen(view);
            }
        });

        usernameText = findViewById(R.id.editText);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /**
     * return the usernameText field as a string (used in another class).
     * @return username of current player as a string
     */
    public static String getUsername(){

        return usernameText.getText().toString();
    }


    /**
     * If "Create account" button is pressed, this method is called
     * and opens up the create account screen.
     * @param view
     */
    public void openCreateAccountActivity(View view){
        Intent intent = new Intent(getApplicationContext(),CreateAccountActivity.class);
        startActivity(intent);
    }
}
