package com.mainapp.serverapp.player;

import java.util.Random;

public class Card {

	private String Suite;
	
	private int Value;
	
	public Card() {
		
		Random rand = new Random();
		
		Value = rand.nextInt(10) + 1; //generates random values 1-10		1 being ace, 10 being 10, jack, queen, king.
		int i = rand.nextInt(4) + 1;
		
		if(i == 1) {
			Suite = "Hearts";
		} else if(i == 2) {
			Suite = "Diamonds";
		} else if (i == 3) {
			Suite = "Spades";
		} else if (i == 4) {
			Suite = "Clubs";
		} else {
			Suite = "Clubs";
		}
	}

	public String getSuite() {
		return Suite;
	}

	public void setSuite(String suite) {
		Suite = suite;
	}

	public int getValue() {
		return Value;
	}

	public void setValue(int value) {
		Value = value;
	}
	
	public String toString(Card card) {
		return ", " + String.valueOf(card.getValue()) + " " + card.getSuite();
	}
	
}
