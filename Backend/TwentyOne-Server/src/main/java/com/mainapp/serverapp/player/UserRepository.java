package com.mainapp.serverapp.player;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
	List<Users> findAll();
	
	//Collection<Users> findById(@Param("id") int id);
	
	Users findByUsername(@Param("username") String username);
	
}