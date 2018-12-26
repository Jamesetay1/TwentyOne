package com.mainapp.serverapp.game;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mainapp.serverapp.admin.Admin;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface GameRepository extends CrudRepository<GamePlayers, Integer> {

	List<GamePlayers> findAll();

	GamePlayers findByid(@Param("id") Integer id);

	void save(String player);

}
