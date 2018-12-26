package jtstats.twentyone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


/**
 * Opens the Single Player launch page
 */
public class SinglePlayerStartActivity extends AppCompatActivity {

    public TextView msgResponse;

    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_start);
        fillInStartInfo();
    }

    /**
     * Continues the user to Single Player
     * @param view
     */
    public void openSinglePlayer(View view) {
        Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
        startActivity(intent);
    }

    /**
     * Populate the stating information block
     */
    public void fillInStartInfo() {
        msgResponse = findViewById(R.id.SPStart_Intro_Text);
        msgResponse.setText(
                "Welcome to Single Player\n" +
                        "In Single Player it is just you against the dealer.\n " +
                        "Mano-a-Mano. No Holds Barred. \n\n" +
                        "Along with playing against the dealer, you will get" +
                        "real time feedback if your play was the right one!\n" +
                        "We will be using the chart below to evaluate your plays.\n" +
                        "\n\t\t\t\t\t\t\t\t Best of Luck,\n" +
                        "\t\t\t\t\t\t\t\t\t\t - TwentyOne Team"
        );
    }
}
