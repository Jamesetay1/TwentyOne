package com.mainapp.serverapp.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainapp.serverapp.lobbies.Status;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/player") // This means URL's start with /demo (after Application path)
public class PlayerController {
	@Autowired // This means to get the bean called userRepository
				// Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser(@RequestBody Users user) {
		
		// @ResponseBody // means the returned String is the response, not a view name
		// @RequestParam // means it is a parameter from the GET or POST request
		PasswordHashes pswd = new PasswordHashes();
		user.setPassword(pswd.passHash(user.getPassword()));
		userRepository.save(user);
		
		return new Status("Saved").toString();
	}

	@GetMapping(path = "/find/all")
	public @ResponseBody Iterable<Users> getAllUsers() {
		// This returns a JSON or XML with the users
		
		return userRepository.findAll();
	}
	
	@GetMapping("/find/{username}")
	public @ResponseBody
	Users findByUsername(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}
	
	@GetMapping("/find/{username}/{password}")
	public @ResponseBody String login(@PathVariable String username, @PathVariable String password) {
		Users attempt = findByUsername(username);
		
		//Creates a Password hash instance to use
		PasswordHashes pswd = new PasswordHashes();
		
		if(attempt == null) {
			return "User not Found";
		}
		
		if(pswd.passCheck(password, attempt.getPassword())) {
			return "Login Success";
		} else {
			return "Incorrect Password";
		}
	}
	
	@GetMapping(path = "/test")
	public @ResponseBody String testing(){
		return "working";
	}
	

	
	
	
	
	@RequestMapping(path = "/error")
	public @ResponseBody String error(){
		return "Information Not Found";
	}
	
	
	
	//TODO implement add with path variables
	/*
	@RequestMapping(method = RequestMethod.POST, value = "/add/{username}/{email}/{password}") // Map ONLY POST Requests
	public @ResponseBody String addNewUser(@PathVariable String username, @PathVariable String email, @PathVariable String password) {

		// @ResponseBody // means the returned String is the response, not a view name
		// @RequestParam // means it is a parameter from the GET or POST request
		
		
		Users postUser = new Users();
		postUser.setUsername(username);
		postUser.setEmail(email);
		postUser.setPassword(password);
		
		userRepository.save(postUser);
		
		return "Saved";
	}
	*/
	
	
}
