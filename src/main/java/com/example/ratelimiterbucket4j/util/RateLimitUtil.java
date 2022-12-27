package com.example.ratelimiterbucket4j.util;

import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class RateLimitUtil {

    public static final List<RateLimitMap> prefixes = Arrays.asList(
            new RateLimitMap("/api/subscriptions", RateLimitApiName.SUBSCRIPTION_LIST),
            new RateLimitMap("/api/subscription", RateLimitApiName.SUBSCRIPTION_BY_ID),
            new RateLimitMap("/api/payments", RateLimitApiName.PAYMENT_LIST),
            new RateLimitMap("/api/payment", RateLimitApiName.PAYMENT_BY_ID)
    );


    public static RateLimitApiName getRateLimitApiName(String requestUri) {
        RateLimitApiName apiName = null;
        for (RateLimitMap entry : prefixes) {
            if (requestUri.startsWith(entry.getPrefix())) {
                apiName = entry.getRateLimitApiName();
                break;
            }
        }
        return apiName;
    }
}
