package com.mainapp.serverapp.leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainapp.serverapp.leaderboard.Leaderboard;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/leaderboards") // This means URL's start with /demo (after Application path)
public class LeaderboardController {
	
	@Autowired
	LeaderboardRepository leaderboardRepositroy;
	
	@GetMapping(path = "/find/all")
	public @ResponseBody Iterable<Leaderboard> getAllUsers() {
		// This returns a JSON or XML with the users
		return leaderboardRepositroy.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public @ResponseBody String addNewLeaderboard(@RequestBody Leaderboard leaderboard) {
		
		leaderboardRepositroy.save(leaderboard);
		
		return new Status("Saved").toString();
	}
	
	//next two methods get the win percentage of the user given the username or id
	@GetMapping(path = "/findusers/{userid}")
	public @ResponseBody double playerWinPercentage(@PathVariable Integer userid) {
		Leaderboard board = leaderboardRepositroy.findByid(userid);
		return board.getWinrate() / 100;
	}
	@GetMapping(path = "/findusers/{username}")
	public @ResponseBody double playerWinPercentage(@PathVariable String username) {
		Leaderboard board = leaderboardRepositroy.findByusername(username);
		return board.getWinrate() / 100;
	}
	
	//next two methods will update the user given the name or id, for having won a game
	@GetMapping(path = "/gamewon/{userid}")
	public @ResponseBody String playerWonHand(@PathVariable Integer userid) {
		Leaderboard board = leaderboardRepositroy.findByid(userid);
		board.setGames(board.getGames() + 1);
		board.setWon(board.getWon() + 1);
		board.setWinrate((board.getWon() / board.getGames()) * 100);
		return new Status("User has been Updated").toString();
	}
	@GetMapping(path = "/gamewon/{username}")
	public @ResponseBody String playerWonHand(@PathVariable String username) {
		Leaderboard board = leaderboardRepositroy.findByusername(username);
		board.setGames(board.getGames() + 1);
		board.setWon(board.getWon() + 1);
		board.setWinrate((board.getWon() / board.getGames()) * 100);
		return new Status("User has been Updated").toString();
	}
	
	//next two methods will update the user given the name or id, fr having played a hand
	@GetMapping(path = "/gamewon/{userid}")
	public @ResponseBody String playerLostHand(@PathVariable Integer userid) {
		Leaderboard board = leaderboardRepositroy.findByid(userid);
		board.setGames(board.getGames() + 1);
		board.setWinrate((board.getWon() / board.getGames()) * 100);
		return new Status("User has been Updated").toString();
	}
	@GetMapping(path = "/gamewon/{username}")
	public @ResponseBody String playerLostHand(@PathVariable String username) {
		Leaderboard board = leaderboardRepositroy.findByusername(username);
		board.setGames(board.getGames() + 1);
		board.setWinrate((board.getWon() / board.getGames()) * 100);
		return new Status("User has been Updated").toString();
	}
	
	//sub class that is used to return a status object in JSON format
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
}


