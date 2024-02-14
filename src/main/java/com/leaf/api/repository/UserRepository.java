package com.leaf.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leaf.api.modal.Users;



@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

	@Query("SELECT U FROM Users U WHERE U.username = ?1")
	Users findByUserName(String userName);

}
