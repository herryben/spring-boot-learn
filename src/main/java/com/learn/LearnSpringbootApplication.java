package com.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableCaching
@EnableRetry
@EnableAspectJAutoProxy
public class LearnSpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnSpringbootApplication.class, args);
	}
}
