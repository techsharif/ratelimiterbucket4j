package com.example.ratelimiterbucket4j.utill;
import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class BucketLimitConverter implements AttributeConverter<Map<RateLimitApiName, Integer>, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<RateLimitApiName, Integer> data) {

        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(data);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return jsonData;
    }

    @Override
    public Map<RateLimitApiName, Integer> convertToEntityAttribute(String jsonData) {

        Map<RateLimitApiName, Integer> data = null;
        try {
            data = objectMapper.readValue(jsonData, Map.class);
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return data;
    }

}