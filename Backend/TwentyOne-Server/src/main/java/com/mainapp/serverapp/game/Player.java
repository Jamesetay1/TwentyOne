package com.mainapp.serverapp.game;

import java.util.ArrayList;

public class Player {
	
	//chips for this player
	//private int chips = 0;
	ArrayList<Integer> hand = new ArrayList<Integer>();
	
	/*
	public int getChips() {
		return chips;
	}
	
	public void setChips(int chips) {
		this.chips = chips;
	}
	
	public void useChips(int chips) {
		this.chips = this.chips - chips;
	}
	
	public void gainChips(int chips) {
		this.chips = this.chips + chips;
	}
	*/
	
	public ArrayList<Integer> getHand() {
		return hand;
	}
	
	public void setHand(ArrayList<Integer> hand) {
		this.hand = hand;
	}
	
	public void newCard() {
		//hand.add();
	}
	//need to create an add function for hitting during your hand
	
	
	
}
