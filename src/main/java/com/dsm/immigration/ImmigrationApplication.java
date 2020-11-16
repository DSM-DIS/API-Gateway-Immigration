package com.dsm.immigration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@EnableZuulServer
@SpringBootApplication
public class ImmigrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(ImmigrationApplication.class, args);
	}
}
