package com.example.ratelimiterbucket4j.service;

import com.example.ratelimiterbucket4j.entity.User;
import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUsers();

    User getUser(Integer id);

    User createUser (User user);

    User changeLimit (Integer id, Map<RateLimitApiName, Integer> limits);

}
