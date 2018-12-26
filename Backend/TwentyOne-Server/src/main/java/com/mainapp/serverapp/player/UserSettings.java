package com.mainapp.serverapp.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


//This class will be used to 
@RestController
@RequestMapping(path = "/player/settings")
public class UserSettings {					//expand as needed for game development
	
	//Different color scheme
	private int theme = 0;
	
	//Different card backs
	private int cards = 0;
	
	//Toggle for stats
	private boolean cardStats = true;
	
	public boolean isCardStats() {
		return cardStats;
	}

	public void setCardStats(boolean cardStats) {
		this.cardStats = cardStats;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public int getCards() {
		return cards;
	}

	public void setCards(int cards) {
		this.cards = cards;
	}

	
	
	

}
