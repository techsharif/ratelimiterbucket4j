package com.example.ratelimiterbucket4j.controller;


import com.example.ratelimiterbucket4j.entity.User;
import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;
import com.example.ratelimiterbucket4j.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/user/{id}")
	public User getUsers(@NotNull @PathVariable Integer id) {
		return userService.getUser(id);
	}

	@PostMapping("/user")
	public User createUser(@NotNull @RequestBody User user) {
		return userService.createUser(user);
	}

	@PostMapping("/user/limit/change/{id}")
	public User changeLimit(@NotNull @PathVariable Integer id,@NotNull @RequestBody Map<RateLimitApiName, Integer> limit) {
		return userService.changeLimit(id, limit);
	}

}
