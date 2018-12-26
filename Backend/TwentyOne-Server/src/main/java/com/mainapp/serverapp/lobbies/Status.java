package com.mainapp.serverapp.lobbies;

public class Status {
	private String message;
	
	
	public Status(String message) {
		this.message = message;
	}
	
	public String toString() {
		String formatted = "{\"Status\" : \"" + this.message + "\"}";
		
		return formatted;
	}
}
