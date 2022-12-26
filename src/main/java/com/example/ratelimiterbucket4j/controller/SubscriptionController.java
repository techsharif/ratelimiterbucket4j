package com.example.ratelimiterbucket4j.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    @GetMapping("/api/subscriptions")
    public String subscriptionList() {
        return "subscription list";
    }

    @GetMapping("/api/subscription")
    public String subscription() {
        return "subscription item";
    }
}
