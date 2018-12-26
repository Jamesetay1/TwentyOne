package jtstats.twentyone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Framework for a future implementation of Settings
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     *  When the Multiplayer View is created, this method creates all of the necessary
     *  information to be displayed to the user
     * @param savedInstanceState  The saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
