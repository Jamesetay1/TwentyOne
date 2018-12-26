package jtstats.twentyone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
import android.util.Log;

/**
 * Class for Single Player and Training Version of 21.
 */
public class SinglePlayerActivity extends AppCompatActivity {

    Random rand = new Random();
    private String[] deck;
    private TextView playerCard1, playerCard2, playerCard3, playerCard4, playerCard5;
    private TextView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5;
    private TextView turnText;

    /**
     * Executes when view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        playerCard1 =  findViewById(R.id.your_hand_card_1);
        playerCard2 =  findViewById(R.id.your_hand_card_2);
        playerCard3 =  findViewById(R.id.your_hand_card_3);
        playerCard4 =  findViewById(R.id.your_hand_card_4);
        playerCard5 =  findViewById(R.id.your_hand_card_5);

        dealerCard1 =  findViewById(R.id.sp_dealer_card_1);
        dealerCard2 =  findViewById(R.id.sp_dealer_card_2);
        dealerCard3 =  findViewById(R.id.sp_dealer_card_3);
        dealerCard4 =  findViewById(R.id.sp_dealer_card_4);
        dealerCard5 =  findViewById(R.id.sp_dealer_card_5);

        turnText = findViewById(R.id.turn_text_view2);

        initDeck();
        initGame();
    }

    /**
     * Create a 52 card deck
     */
    public void initDeck() {

        String temp[] = {"1", "1", "1", "1",
                        "2", "2", "2", "2",
                        "3", "3", "3", "3",
                        "4", "4", "4", "4",
                        "5", "5", "5", "5",
                        "6", "6", "6", "6",
                        "7", "7", "7", "7",
                        "8", "8", "8", "8",
                        "9", "9", "9", "9",
                        "10", "10", "10", "10",
                        "Jack", "Jack", "Jack", "Jack",
                        "Queen", "Queen", "Queen", "Queen",
                        "King", "King", "King", "King"};

        deck = temp;
    }

    /**
     * start the game
     */
    public void initGame() {

        int randCard = rand.nextInt(52);
        playerCard1.setText(deck[randCard]);
        randCard = rand.nextInt(52);
        playerCard2.setText(deck[randCard]);

        randCard = rand.nextInt(52);
        dealerCard1.setText(deck[randCard]);
        randCard = rand.nextInt(52);
        dealerCard2.setText(deck[randCard]);

        String turn = "It's your turn!";
        turnText.setText(turn);
    }

    /**
     * Retrieve a players hand
     * @return value of player's hand
     */
    public int getPlayerHand() {

        int sum = 0;

        if (playerCard1.getText().equals("Jack") || playerCard1.getText().equals("Queen") || playerCard1.getText().equals("King")) {
            sum += 10;
        }
        else {
            sum += Integer.parseInt(playerCard1.getText().toString());
        }

        if (playerCard2.getText().equals("Jack") || playerCard2.getText().equals("Queen") || playerCard2.getText().equals("King")) {
            sum += 10;
        }
        else {
            sum += Integer.parseInt(playerCard2.getText().toString());
        }

        if (playerCard3.getText().equals("Jack") || playerCard3.getText().equals("Queen") || playerCard3.getText().equals("King")) {
            sum += 10;
        }
        else if (!playerCard3.getText().equals("+")) {
            sum += Integer.parseInt(playerCard3.getText().toString());
        }

        if (playerCard4.getText().equals("Jack") || playerCard4.getText().equals("Queen") || playerCard4.getText().equals("King")) {
            sum += 10;
        }
        else if (!playerCard4.getText().equals("+")) {
            sum += Integer.parseInt(playerCard4.getText().toString());
        }

        if (playerCard5.getText().equals("Jack") || playerCard5.getText().equals("Queen") || playerCard5.getText().equals("King")) {
            sum += 10;
        }
        else if (!playerCard5.getText().equals("+")) {
            sum += Integer.parseInt(playerCard5.getText().toString());
        }

        Log.d("test sum", sum + "");
        return sum;
    }

    /**
     * Indicates the player went over 21
     * @return true if the player went over or not
     */
    public boolean playerWentOver() {

        if (getPlayerHand() > 21) {
            return true;
        }
        return false;
    }

    /**
     * Takes 'AI' turn for the dealer.
     */
    public void getDealerTurn() {

        if (getDealerHand() > 16) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}

            endGame();
        }
        else {
            int randCard = rand.nextInt(52);

            if (dealerCard3.getText().equals("+")) {
                dealerCard3.setText(deck[randCard]);
            }
            else if (dealerCard4.getText().equals("+")) {
                dealerCard4.setText(deck[randCard]);
            }
            else if (dealerCard5.getText().equals("+")) {
                dealerCard5.setText(deck[randCard]);
            }

            getDealerTurn();
        }
    }

    /**
     * Retrieves the dealer hand
     * @return value of dealer's hand
     */
    public int getDealerHand() {

        int sum = 0;

        if (dealerCard1.getText().equals("Jack") || dealerCard1.getText().equals("Queen") || dealerCard1.getText().equals("King")) {
            sum += 10;
        }
        else {
            sum += Integer.parseInt(dealerCard1.getText().toString());
        }

        if (dealerCard2.getText().equals("Jack") || dealerCard2.getText().equals("Queen") || dealerCard2.getText().equals("King")) {
            sum += 10;
        }
        else {
            sum += Integer.parseInt(dealerCard2.getText().toString());
        }

        if (dealerCard3.getText().equals("Jack") || dealerCard3.getText().equals("Queen") || dealerCard3.getText().equals("King")) {
            sum += 10;
        }
        else if (!dealerCard3.getText().equals("+")) {
            sum += Integer.parseInt(dealerCard3.getText().toString());
        }

        if (dealerCard4.getText().equals("Jack") || dealerCard4.getText().equals("Queen") || dealerCard4.getText().equals("King")) {
            sum += 10;
        }
        else if (!dealerCard4.getText().equals("+")) {
            sum += Integer.parseInt(dealerCard4.getText().toString());
        }

        if (dealerCard5.getText().equals("Jack") || dealerCard5.getText().equals("Queen") || dealerCard5.getText().equals("King")) {
            sum += 10;
        }
        else if (!dealerCard5.getText().equals("+")) {
            sum += Integer.parseInt(dealerCard5.getText().toString());
        }

        return sum;
    }

    /**
     * Handles when the user 'hits'
     * Generally, it will give them a card
     * and check if they went over or not.
     * @param view
     */
    public void handleHitButton(View view) {

        if (turnText.getText().equals("It's the dealer's turn!")) {
            return;
        }

        int randCard = rand.nextInt(52);

        if (playerCard3.getText().equals("+")) {
            playerCard3.setText(deck[randCard]);
        }
        else if (playerCard4.getText().equals("+")) {
            playerCard4.setText(deck[randCard]);
        }
        else if (playerCard5.getText().equals("+")) {
            playerCard5.setText(deck[randCard]);
        }

        if (playerWentOver()) {
            endGame();
        }
    }

    /**
     * Handles when the user decides to 'stay'
     * @param view
     */
    public void handleStayButton(View view) {

        String dealerTurn = "It's the dealer's turn!";
        turnText.setText(dealerTurn);

        getDealerTurn();
    }

    /**
     * Handles when the uesr decides to 'split'
     * @param view
     */
    public void handleSplitButton(View view) {

    }

    /**
     * Handles end game scenarios and decides the winner
     */
    public void endGame() {

        String endMessage = "";

        if (getPlayerHand() > 21) {
            endMessage = "You went over 21, the Dealer won with " + getDealerHand() + "!";
            turnText.setText(endMessage);
        }
        else if (getPlayerHand() <= 21 && getDealerHand() > 21) {
            endMessage = "The Dealer went over 21, you won with " + getPlayerHand() + "!";
            turnText.setText(endMessage);
        }
        else if (getPlayerHand() > getDealerHand()) {
            endMessage = "You won with " + getPlayerHand() + "!";
            turnText.setText(endMessage);
        }
        else if (getPlayerHand() == getDealerHand()) {
            endMessage = "You both tied at " + getPlayerHand() + "!";
            turnText.setText(endMessage);
        }
        else {
            endMessage = "The Dealer won with " + getDealerHand() + "!";
            turnText.setText(endMessage);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(SinglePlayerActivity.this);
        builder.setTitle(turnText.getText());
        builder.setMessage("Would you like to play again?");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(),SinglePlayerActivity.class);
                startActivity(intent);
            }
        });

        builder.show();
    }
}
