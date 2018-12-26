package jtstats.twentyone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Displayed Info about TwentyOne
 */
public class InfoActivity extends AppCompatActivity {

    /**
     * Message response to be changed
     */
    public TextView msgResponse;

    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        fillInInfo();
    }

    /**
     * Adds the information onto the information screen.
     * Formatted and should be easily changeable.
     */
    public void fillInInfo(){
        msgResponse =  findViewById(R.id.infoText);
        msgResponse.setText(
                "At its core, TwentyOne is an all in one Blackjack application." +
                "It features both online gameplay with other users, as well as " +
                "a single player training feature\n\n" +
                "We hope you enjoy our application, please send any ideas, " +
                "questions, or complaints to TwentyOneGame@gmail.com\n" +
                "\n\t\t\t\t\t\t\t\t Best,\n"+
                        "\t\t\t\t\t\t\t\t\t\t - TwentyOne Team"
        );

    }
}
