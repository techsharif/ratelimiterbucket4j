package com.example.ratelimiterbucket4j.service;

import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;
import io.github.bucket4j.Bucket;

public interface RateLimitService {
    Bucket resolveBucket(RateLimitApiName apiName, String apiKey);

    void updateBucket(RateLimitApiName apiName, String apiKey);


}
