package jtstats.twentyone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Main Hub for TwentyOne, Start page.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Opens multiplayer screen to log in
     * @param view
     */
    public void openLogInScreen(View view){
        Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
        startActivity(intent);
    }

    /**
     * opens singleplayer screen
     * @param view
     */
    public void openSinglePlayerScreen(View view){
        Intent intent = new Intent(getApplicationContext(),SinglePlayerStartActivity.class);
        startActivity(intent);
    }

    /**
     * opens info screen (information about app)
     * @param view
     */
    public void openInfoScreen(View view){
        Intent intent = new Intent(getApplicationContext(),InfoActivity.class);
        startActivity(intent);

    }


    /**
     * opens settings screen (SP/MP settings)
     * @param view
     */
    public void openSettingsScreen(View view){
        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(intent);

    }

}

