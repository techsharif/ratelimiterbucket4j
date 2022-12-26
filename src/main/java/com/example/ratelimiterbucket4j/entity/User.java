package com.example.ratelimiterbucket4j.entity;


import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;
import com.example.ratelimiterbucket4j.util.BucketLimitConverter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "api_key")
	private String apiKey;

	private String name;

	@Column(columnDefinition = "text")
	@Convert(converter = BucketLimitConverter.class)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<RateLimitApiName, Integer> limits;
	
}
