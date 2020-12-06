package com.dsm.immigration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@EnableZuulServer
@SpringBootApplication
public class ImmigrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(ImmigrationApplication.class, args);
	}
}