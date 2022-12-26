package com.example.ratelimiterbucket4j.util;

import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;

public class RateLimitMap {
    private String prefix;

    private RateLimitApiName rateLimitApiName;

    public RateLimitMap(String prefix, RateLimitApiName rateLimitApiName) {
        this.prefix = prefix;
        this.rateLimitApiName = rateLimitApiName;
    }

    public String getPrefix() {
        return prefix;
    }

    public RateLimitApiName getRateLimitApiName() {
        return rateLimitApiName;
    }
}