package com.mainapp.serverapp.lobbies;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "lobbies")
public class Lobby {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "playerone")
	private String playerone;
	
	@Column(name = "playertwo")
	private String playertwo;
	
	@Column(name = "playerthree")
	private String playerthree;
	
	@Column(name = "playerfour")
	private String playerfour;
	
	public Integer getid() {
		return id;
	}

	public void setLobbyid(Integer id) {
		this.id = id;
	}

	public String getPlayerOne() {
		return playerone;
	}

	public void setPlayerOne(String playerOne) {
		this.playerone = playerOne;
	}

	public String getPlayerTwo() {
		return playertwo;
	}

	public void setPlayerTwo(String playerTwo) {
		this.playertwo = playerTwo;
	}

	public String getPlayerThree() {
		return playerthree;
	}

	public void setPlayerThree(String playerThree) {
		this.playerthree = playerThree;
	}

	public String getPlayerFour() {
		return playerfour;
	}

	public void setPlayerFour(String playerFour) {
		this.playerfour = playerFour;
	}
	
	
}
