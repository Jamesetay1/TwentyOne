package com.mainapp.serverapp.lobbies;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface LobbyRepository extends CrudRepository<Lobby, Integer> {
	
	Lobby findByid(@Param("id") Integer id);
	
	//Lobby numberofPlayers(@Param("id") Integer id);
	
}