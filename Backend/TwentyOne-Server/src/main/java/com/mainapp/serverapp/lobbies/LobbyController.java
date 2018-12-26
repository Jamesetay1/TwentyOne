package com.mainapp.serverapp.lobbies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainapp.serverapp.player.Users;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/lobby") // This means URL's start with /demo (after Application path)
public class LobbyController {

	@Autowired
	LobbyRepository lobbyRepository;

	@GetMapping(path = "/find/all")
	public @ResponseBody Iterable<Lobby> getAllUsers() {
		// This returns a JSON or XML with the users
		return lobbyRepository.findAll();
	}
	
	@GetMapping("/find/{lobbyid}")
	public @ResponseBody Lobby findByUsername(@PathVariable Integer lobbyid) {
		return lobbyRepository.findByid(lobbyid);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser(@RequestBody Lobby lobby) {

		// @ResponseBody // means the returned String is the response, not a view name
		// @RequestParam // means it is a parameter from the GET or POST request
		
		lobbyRepository.save(lobby);
		
		return new Status("Saved").toString();
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/joinlobby")
	public @ResponseBody String joinlobby1(@RequestBody Users user) {
		Lobby lobbyone = lobbyRepository.findByid(1);
			//I do believe this does the same as all of the below if statements, just to make it look nicer
		if (lobbyone.getPlayerOne().equals(user.getUsername()) || lobbyone.getPlayerTwo().equals(user.getUsername()) || 
				lobbyone.getPlayerThree().equals(user.getUsername()) || lobbyone.getPlayerFour().equals(user.getUsername())) {
			//return "{\"status\" : \"User with that name is already playing!\"}";
			return new Status("This Name is already in Use").toString();	
		}	

		if (lobbyone.getPlayerOne().equals("empty")) {
			lobbyone.setPlayerOne(user.getUsername());
			Lobby updatedLobby = lobbyRepository.save(lobbyone);
			//return "{\"status\" : \"You are PLAYER ONE\"}";
			//return "{\"status\" : \"User with that name is already playing!\"}";
			return new Status("You are PLAYER ONE").toString();
		}

		if (lobbyone.getPlayerTwo().equals("empty")) {
			lobbyone.setPlayerTwo(user.getUsername());
			Lobby updatedLobby = lobbyRepository.save(lobbyone);
			//return "{\"status\" : \"You are PLAYER TWO\"}";
			return new Status("You are PLAYER TWO").toString();
		}

		if (lobbyone.getPlayerThree().equals("empty")) {
			lobbyone.setPlayerThree(user.getUsername());
			Lobby updatedLobby = lobbyRepository.save(lobbyone);
			//return "{\"status\" : \"You are PLAYER THREE\"}";
			return new Status("You are PLAYER THREE").toString();
		}
		
		if (lobbyone.getPlayerFour().equals("empty")) {
			lobbyone.setPlayerFour(user.getUsername());
			Lobby updatedLobby = lobbyRepository.save(lobbyone);
			//return "{\"status\" : \"You are PLAYER FOUR\"}";
			return new Status("You are PLAYER FOUR").toString();
		}

		//return "Lobby is Full";
		return new Status("Lobby is FULL").toString();
	}
	
	@GetMapping(path = "/resetlobby")
	public @ResponseBody String resetLobby1() {
		
		Lobby lobbyone = lobbyRepository.findByid(1);
		
		lobbyone.setPlayerOne("empty");
		lobbyone.setPlayerTwo("empty");
		lobbyone.setPlayerThree("empty");
		lobbyone.setPlayerFour("empty");
		
		Lobby updatedLobby = lobbyRepository.save(lobbyone);
		
		return new Status("Lobby One Is Reset").toString();
	}
	
	@GetMapping(path = "/findusers/{lobbyid}")
	public @ResponseBody int numberofPlayers(@PathVariable Integer lobbyid) {
		Lobby lobby = lobbyRepository.findByid(lobbyid);
		int results = 0;
		if(lobby.getPlayerFour().equals("empty")) {
			results++;
		}
		if(lobby.getPlayerThree().equals("empty")) {
			results++;
		}
		if(lobby.getPlayerTwo().equals("empty")) {
			results++;
		}
		if(lobby.getPlayerOne().equals("empty")) {
			results++;
		}
		return 4 - results;
	}
	
}
