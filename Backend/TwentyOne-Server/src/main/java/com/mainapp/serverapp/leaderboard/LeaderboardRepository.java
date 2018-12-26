package com.mainapp.serverapp.leaderboard;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mainapp.serverapp.lobbies.Lobby;


@Repository
public interface LeaderboardRepository extends CrudRepository<Leaderboard, Integer>{

	List<Leaderboard> findAll();
	
	Leaderboard findByusername(@Param("username") String username);
	Leaderboard findByid(@Param("id") Integer id);
	
}
