package com.example.ratelimiterbucket4j.service;

import com.example.ratelimiterbucket4j.entity.User;
import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;
import com.example.ratelimiterbucket4j.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RateLimitService rateLimitService;

    public UserServiceImpl(UserRepository userRepository, RateLimitService rateLimitService) {
        this.userRepository = userRepository;
        this.rateLimitService = rateLimitService;
    }


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
        }
        return userOptional.get();
    }



    @Override
    public User createUser(User user) {
        if (user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id should be null");
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Override
    public User changeLimit(Integer id, Map<RateLimitApiName, Integer> limits) {
        User user = getUser(id);
        User finalUser = user;
        limits.forEach(
                (apiName, limit) -> {
                    finalUser.getLimits().put(apiName, limit);
                }
        );
        user = userRepository.save(finalUser);
        changeRateLimitConfiguration(user, limits);
        return user;
    }

    private void changeRateLimitConfiguration(User user, Map<RateLimitApiName, Integer> limits) {
        limits.forEach(
                (apiName, limit) -> {
                    rateLimitService.updateBucket(apiName, user.getApiKey());
                }
        );
    }
}
