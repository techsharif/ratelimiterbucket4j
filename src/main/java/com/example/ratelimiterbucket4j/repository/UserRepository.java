package com.example.ratelimiterbucket4j.repository;

import com.example.ratelimiterbucket4j.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByApiKey(String apiKey);

	List<User> findAll();

}
