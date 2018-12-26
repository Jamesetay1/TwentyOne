package com.mainapp.serverapp.game;

import com.mainapp.serverapp.player.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "game")
public class GamePlayers {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "playerone")
	private String playerOne;
	
	@Column(name = "playeronehand") 
	String playerOneHand;
	
	@Column(name = "playeronevalue")
	private int playerOneValue;

	@Column(name = "playertwohand")
	private String playerTwoHand;
	
	@Column(name = "playertwo")
	private String playerTwo;
	
	@Column(name = "playertwovalue")
	private int playerTwoValue;
	
	@Column(name = "playerthree")
	private String playerThree;
	
	@Column(name = "playerthreehand")
	private String playerThreeHand;
	
	@Column(name = "playerthreevalue")
	private int playerThreeValue;
	
	@Column(name = "playerfour")
	private String playerFour;
	
	@Column(name = "playerfourhand")
	private String playerFourHand;
	
	@Column(name = "playerfourvalue")
	private int playerFourValue;	
	
	@Column(name = "dealerhand")
	private String dealerHand;
	
	@Column(name = "dealervalue")
	private int dealerValue;
	
	@Column(name = "playerturn")
	private int playerTurn;
	
	public String getPlayerTwoHand() {
		return playerTwoHand;
	}

	public void setPlayerTwoHand(String playerTwoHand) {
		this.playerTwoHand = playerTwoHand;
	}

	public String getPlayerOneHand() {
		return playerOneHand;
	}

	public void setPlayerOneHand(String playerOneHand) {
		this.playerOneHand = playerOneHand;
	}

	public String getPlayerThreeHand() {
		return playerThreeHand;
	}

	public void setPlayerThreeHand(String playerThreeHand) {
		this.playerThreeHand = playerThreeHand;
	}

	public String getPlayerFourHand() {
		return playerFourHand;
	}

	public void setPlayerFourHand(String playerFourHand) {
		this.playerFourHand = playerFourHand;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(String playerOne) {
		this.playerOne = playerOne;
	}

	public String getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(String playerTwo) {
		this.playerTwo = playerTwo;
	}

	public String getPlayerThree() {
		return playerThree;
	}

	public void setPlayerThree(String playerThree) {
		this.playerThree = playerThree ;
	}

	public String getPlayerFour() {
		return playerFour;
	}

	public void setPlayerFour(String playerFour) {
		this.playerFour = playerFour;
	}

	public String getDealerHand() {
		return dealerHand;
	}

	public void setDealerHand(String dealerHand) {
		this.dealerHand = dealerHand;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}
	
	public GamePlayers() {
		playerOne = playerOneHand = playerTwo = playerTwoHand = playerThree = playerThreeHand = playerFour = playerFourHand = dealerHand = "empty";
		playerOneValue = playerTwoValue = playerThreeValue = playerFourValue = 0;
		playerTurn = 1;
	}

	public void addtoHand(int i, Card card) {
		
		switch(i) {
		case 1:
			setPlayerOneHand(playerOneHand + card.toString(card));
			break;
		case 2:
			setPlayerTwoHand(playerTwoHand + card.toString(card));
			break;
		case 3:
			setPlayerThreeHand(playerThreeHand + card.toString(card));
			break;
		case 4:
			setPlayerFourHand(playerFourHand + card.toString(card));
			break;
		case 5:
			setDealerHand(dealerHand + card.toString(card));
			break;
		}
	}
	
	//prototype for spilt function
	public String add(int i, Card[] cards) {
		
		switch(i) {
		case 1:
			setPlayerOne(playerOne + cards[0].toString(cards[0]) + cards[1].toString(cards[1]));
			break;
		case 2:
			setPlayerTwo(playerTwo + cards[0].toString(cards[0]) + cards[1].toString(cards[1]));
			break;
		case 3:
			setPlayerThree(playerThree + cards[0].toString(cards[0]) + cards[1].toString(cards[1]));
			break;
		case 4:
			setPlayerFour(playerFour + cards[0].toString(cards[0]) + cards[1].toString(cards[1]));
			break;
		}
		return "what";
	}
	
	//Follow are for hand Values
	//need to fix this so it only sets the values
	public int getPlayerOneValue() {
		return playerOneValue;
	}

	public void setPlayerOneValue(int playerOneValue) {
		this.playerOneValue = playerOneValue;
	}

	public int getPlayerTwoValue() {
		return playerTwoValue;
	}

	public void setPlayerTwoValue(int playerTwoValue) {
		this.playerTwoValue = playerTwoValue;
	}

	public int getPlayerThreeValue() {
		return playerThreeValue;
	}

	public void setPlayerThreeValue(int playerThreeValue) {
		this.playerThreeValue = playerThreeValue;
	}

	public int getPlayerFourValue() {
		return playerFourValue;
	}

	public void setPlayerFourValue(int playerFourValue) {
		this.playerFourValue = playerFourValue;
	}

	public int getDealerValue() {
		return dealerValue;
	}

	public void setDealerValue(int dealerValue) {
		this.dealerValue = dealerValue;
	}
}
