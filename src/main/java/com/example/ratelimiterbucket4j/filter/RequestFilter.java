package com.example.ratelimiterbucket4j.filter;


import com.example.ratelimiterbucket4j.enumeration.RateLimitApiName;
import com.example.ratelimiterbucket4j.service.RateLimitService;
import com.example.ratelimiterbucket4j.util.RateLimitUtil;
import io.github.bucket4j.Bucket;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RequestFilter extends OncePerRequestFilter {

	private final RateLimitService rateLimitService;

	public RequestFilter(RateLimitService rateLimitService) {
		this.rateLimitService = rateLimitService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		RateLimitApiName apiName = RateLimitUtil.getRateLimitApiName(request.getRequestURI());

		if (apiName == null) {
			log.info("Don't need to limit");
			filterChain.doFilter(request, response);
		} else {
			String apiKey = request.getHeader("X-API-KEY");
			if (StringUtils.isNotBlank(apiKey)) {
				Bucket bucket = rateLimitService.resolveBucket(apiName, apiKey);
				if (bucket.tryConsume(1)) {
					filterChain.doFilter(request, response);
				} else {
					throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS);
				}
			} else {
				throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS);
			}
		}

	}

}
