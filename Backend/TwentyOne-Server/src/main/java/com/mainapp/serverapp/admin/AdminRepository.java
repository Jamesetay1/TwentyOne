package com.mainapp.serverapp.admin;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mainapp.serverapp.player.Users;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {
	List<Admin> findAll();

	Collection<Admin> findById(@Param("id") int id);

	Admin findByUsername(@Param("username") String username);

}
