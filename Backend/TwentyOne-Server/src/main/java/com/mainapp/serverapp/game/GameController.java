package com.mainapp.serverapp.game;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainapp.serverapp.lobbies.Lobby;
import com.mainapp.serverapp.lobbies.Status;
import com.mainapp.serverapp.player.Card;

@RestController
@RequestMapping(path = "/game")
public class GameController{

	@Autowired
	GameRepository gameRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/add") // Map ONLY POST Requests
	public @ResponseBody String addNewGame(@RequestBody GamePlayers game) {

		// @ResponseBody // means the returned String is the response, not a view name
		// @RequestParam // means it is a parameter from the GET or POST request

		gameRepository.save(game);	//for whatever reason this isnt working even though
										//repository does have a save function

		return new Status("Saved").toString();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/hit/{Player}")
	public @ResponseBody Card gameHit(@PathVariable Integer id, @PathVariable String Player) {

		Card card = new Card();
		
		GamePlayers current = gameRepository.findByid(id);
		
		
		
		
		if (Player.equals("playerOne") && current.getPlayerTurn() == 1) {
			current.addtoHand(1, card);
			current.setPlayerOneValue(current.getPlayerOneValue() + card.getValue());
			
		} else if (Player.equals("playerTwo") && current.getPlayerTurn() == 2) {
			current.addtoHand(2, card);
			current.setPlayerTwoValue(current.getPlayerTwoValue() + card.getValue());
			
		} else if (Player.equals("playerThree") && current.getPlayerTurn() == 3) {
			current.addtoHand(3, card);
			current.setPlayerThreeValue(current.getPlayerThreeValue() + card.getValue());
			
		} else if (Player.equals("playerFour") && current.getPlayerTurn() == 4) {
			current.addtoHand(4, card);
			current.setPlayerFourValue(current.getPlayerFourValue() + card.getValue());
			
		}
		
		gameRepository.save(current);
		
		return card;
	}
	
	//This is just the prototype for split
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/split")
	public @ResponseBody Card[] gameSplit(@PathVariable Integer id, @RequestBody String Player) {
		
		Card[] cards = {new Card(), new Card()};
		
		GamePlayers current = gameRepository.findByid(id);
		
		if (Player.equals("playerOne")) {
			current.add(1, cards);
		} else if (Player.equals("playerTwo")) {
			current.add(2, cards);
		} else if (Player.equals("playerThree")) {
			current.add(3, cards);
		} else if (Player.equals("playerFour")) {
			current.add(4, cards);
		}
		
		gameRepository.save(current);
		
		return cards;
	}
	
	// game/findall
	@GetMapping(path = "/find/all")
	public @ResponseBody Iterable<GamePlayers> findAllGames() {
		return gameRepository.findAll();
	}

	// game/find/1/player2hand
	@GetMapping(path = "/find/{gameid}/playerOneHand")
	public @ResponseBody String player1Hand(@PathVariable Integer gameid) {
		GamePlayers current = gameRepository.findByid(gameid);
		return current.getPlayerOneHand();
	}
	
	@GetMapping(path = "/find/{gameid}/playerTwoHand")
	public @ResponseBody String player2Hand(@PathVariable Integer gameid) {
		GamePlayers current = gameRepository.findByid(gameid);
		return current.getPlayerTwoHand();
	}
	
	@GetMapping(path = "/find/{gameid}/playerThreeHand")
	public @ResponseBody String player3Hand(@PathVariable Integer gameid) {
		GamePlayers current = gameRepository.findByid(gameid);
		return current.getPlayerThreeHand();
	}
	
	@GetMapping(path = "/find/{gameid}/playerFourHand")
	public @ResponseBody String player4Hand(@PathVariable Integer gameid) {
		GamePlayers current = gameRepository.findByid(gameid);
		return current.getPlayerFourHand();
	}
	
	@GetMapping(path = "/find/{gameid}/dealerHand")
	public @ResponseBody String DealerHand(@PathVariable Integer gameid) {
		GamePlayers current = gameRepository.findByid(gameid);
		return current.getDealerHand();
	}
	
	//this is where we will start the hand string
	@RequestMapping(method = RequestMethod.PUT, value = "/{gameid}/startgame")
	public void gameinit(@PathVariable Integer gameid, @RequestBody Lobby lobby) {
		GamePlayers current = gameRepository.findByid(gameid);
		
		//set Player names
		current.setPlayerOne(lobby.getPlayerOne());
		current.setPlayerTwo(lobby.getPlayerTwo());
		current.setPlayerThree(lobby.getPlayerThree());
		current.setPlayerFour(lobby.getPlayerFour());
		
		Card card = new Card();
		
		//player 1 first two cards
		card = new Card();
		current.setPlayerOneHand("" + card.getValue() + " " + card.getSuite());
		current.setPlayerOneValue(card.getValue() + current.getPlayerOneValue());
		card = new Card();
		current.addtoHand(1, card);
		current.setPlayerOneValue(card.getValue()  + current.getPlayerOneValue());
		
		//player 2 first two cards
		card = new Card();
		current.setPlayerTwoHand("" + card.getValue() + " " + card.getSuite());
		current.setPlayerTwoValue(card.getValue() + current.getPlayerTwoValue());
		card = new Card();
		current.addtoHand(2, card);
		current.setPlayerTwoValue(card.getValue() + current.getPlayerTwoValue());
		
		//player 3 first two cards
		if(!current.getPlayerThree().equals("empty")){
			card = new Card();
			current.setPlayerThreeHand("" + card.getValue() + " " + card.getSuite());
			current.setPlayerThreeValue(card.getValue() + current.getPlayerThreeValue());
			card = new Card();
			current.addtoHand(3, card);
			current.setPlayerThreeValue(card.getValue() + current.getPlayerThreeValue());
		}
		//player 4 first two cards
		if(!current.getPlayerFour().equals("empty")){
			card = new Card();
			current.setPlayerFourHand("" + card.getValue() + " " + card.getSuite());
			current.setPlayerFourValue(card.getValue()  + current.getPlayerFourValue());
			card = new Card();
			current.addtoHand(4, card);
			current.setPlayerFourValue(card.getValue() + current.getPlayerFourValue());
		}
		//dealer is given two cards
		card = new Card();
		current.setDealerHand("" + card.getValue() + " " + card.getSuite());
		current.setDealerValue(card.getValue() + current.getDealerValue());
		card = new Card();
		current.addtoHand(5, card);
		current.setDealerValue(card.getValue() + current.getDealerValue());
		gameRepository.save(current);
	}
	
	@GetMapping(path = "/{gameid}/dealerTurn")
	public @ResponseBody String dealerTurn(@PathVariable Integer gameid) {
		
		GamePlayers current = gameRepository.findByid(gameid);
		Card card;
		while(current.getDealerValue() < 17) {
			card = new Card();
			current.addtoHand(5,card);
			current.setDealerValue(card.getValue() + current.getDealerValue());
			gameRepository.save(current);
		}
		
		return "Dealer's Turn Complete";
		
	}
	
	@GetMapping(path = "/{gameid}/dealerHand")
	public String dealerHand(@PathVariable Integer gameid) {
		GamePlayers current = gameRepository.findByid(gameid);
		return current.getDealerHand();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/stay/{Player}")
	public void gameStay(@PathVariable Integer id, @PathVariable String Player) {
		GamePlayers current = gameRepository.findByid(id);
		
		if(Player.equals("playerOne") && current.getPlayerTurn() == 1) {
			current.setPlayerTurn(2);	
		}
		//if player 2 turn and they requested to stay, with a player 3 in the lobby
		//turn will be set to 3, otherwise turn will be set to 5 (dealer)
		else if(Player.equals("playerTwo") && current.getPlayerTurn() == 2){ 
			if(current.getPlayerThree().equals("empty")) {
				current.setPlayerTurn(5);
			}
			else {
				current.setPlayerTurn(3);	
			}
		}
		//if player 3 turn and they requested to stay, with a player 4 in the lobby
		//turn will be set to 4, otherwise turn will be set to 5 (dealer)
		else if(Player.equals("playerThree") && current.getPlayerTurn() == 3) {
			if(current.getPlayerFour().equals("empty")) {
				current.setPlayerTurn(5);
			}
			else {
				current.setPlayerTurn(4);	
			}
		}
		//we can assume if it has made it to player 4 we can just move to the dealer's turn
		else if(Player.equals("playerFour") && current.getPlayerTurn() == 4) {
			current.setPlayerTurn(5);
		}
		else if(current.getPlayerTurn() == 5) {
			
		}
		gameRepository.save(current);
		
	}
	
	@GetMapping(path = "/find/{gameid}/playerTurn")
	public @ResponseBody Integer playerTurn(@PathVariable Integer gameid) {
		GamePlayers current = gameRepository.findByid(gameid);
		return current.getPlayerTurn();
	}
	
	
	@GetMapping(path = "/{gameid}/winner")
	public @ResponseBody String getplayerWinner(@PathVariable Integer gameid) {
		GamePlayers current = gameRepository.findByid(gameid);
		
		ArrayList<Integer> Values = new ArrayList<Integer>();
		
		Values.add(current.getPlayerOneValue());
		Values.add(current.getPlayerTwoValue());
		Values.add(current.getPlayerThreeValue());
		Values.add(current.getPlayerFourValue());
		Values.add(current.getDealerValue());
		
		int index = 0;
		int max = 0;
		
		for (int i = 0; i < 5; i++) {
			if (Values.get(i) > max && Values.get(i) <= 21) {
				index = i;
				max = Values.get(i);
			}
		}
		
		if (index == 0) {
			return "PlayerOne";
		} else if (index == 1) {
			return "PlayerTwo";
		} else if (index == 2) {
			return "PlayerThree";
		} else if (index == 3) {
			return "PlayerFour";
		} else {
			return "Dealer";
		}
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/gameReset")
	public void gameReset(@PathVariable Integer id) {
		GamePlayers current = new GamePlayers();
		current.setId(id);
		gameRepository.save(current);
	}
	
}
