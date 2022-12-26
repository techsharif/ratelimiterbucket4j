package com.example.ratelimiterbucket4j.service;

import com.example.ratelimiterbucket4j.entity.User;
import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;
import com.example.ratelimiterbucket4j.repository.UserRepository;
import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@Slf4j
public class RateLimitServiceImpl implements RateLimitService{

    private final UserRepository userRepository;
    private final ProxyManager<String> proxyManager;

    public RateLimitServiceImpl(UserRepository userRepository, ProxyManager<String> proxyManager) {
        this.userRepository = userRepository;
        this.proxyManager = proxyManager;
    }

    @Override
    public Bucket resolveBucket(RateLimitApiName apiName, String apiKey) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(apiName, apiKey);
        return proxyManager.builder().build(getKey(apiName, apiKey), configSupplier);
    }

    @Override
    public void updateBucket(RateLimitApiName apiName, String apiKey) {
        Bucket bucket = proxyManager.builder().build(getKey(apiName, apiKey), () -> (createBucketConfiguration(apiName, apiKey)));
        bucket.replaceConfiguration(createBucketConfiguration(apiName, apiKey), TokensInheritanceStrategy.ADDITIVE);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUser(RateLimitApiName apiName, String apiKey) {
        return () -> (createBucketConfiguration(apiName, apiKey));
    }

    private BucketConfiguration createBucketConfiguration(RateLimitApiName apiName, String apiKey){
        User user = getUserByApiKey(apiKey);
        Integer userLimit = getLimit(user.getLimits(), apiName);

        Refill refill = Refill.intervally(userLimit, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(userLimit, refill);

        return BucketConfiguration.builder()
                .addLimit(limit)
                .build();
    }

    private String getKey(RateLimitApiName apiName, String apiKey){
        return apiName + "_" + apiKey;
    }

    private User getUserByApiKey(String apiKey) {
        Optional<User> userOptional = userRepository.findByApiKey(apiKey);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
        }
        return userOptional.get();
    }

    private Integer getLimit(Map<RateLimitApiName, Integer> data, RateLimitApiName apiName) {
        Integer limit = 5; // todo: default

        for (Map.Entry<RateLimitApiName, Integer> entry : data.entrySet()) {
            if (String.valueOf(entry.getKey()).equals(String.valueOf(apiName))) return entry.getValue();
        }

        return limit;
    }
}
