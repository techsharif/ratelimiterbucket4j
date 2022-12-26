package com.example.ratelimiterbucket4j.util;

import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;

import java.util.Map;

public class RateLimitUtil {
    public static final Map<String, RateLimitApiName> prefixes = Map.of(
            "/api/subscriptions", RateLimitApiName.SUBSCRIPTION_LIST,
            "/api/subscription", RateLimitApiName.SUBSCRIPTION_BY_ID,
            "/api/payments", RateLimitApiName.PAYMENT_LIST,
            "/api/payment", RateLimitApiName.PAYMENT_BY_ID
    );

    public static RateLimitApiName getRateLimitApiName(String requestUri) {
        RateLimitApiName apiName = null;
        for (Map.Entry<String, RateLimitApiName> entry : prefixes.entrySet()) {
            if (requestUri.startsWith(entry.getKey())) {
                apiName = entry.getValue();
            }
        }
        return apiName;
    }
}
