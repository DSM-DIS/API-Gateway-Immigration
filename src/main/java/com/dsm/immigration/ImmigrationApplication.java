package com.dsm.immigration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImmigrationApplication {
	public static void main(String[] args) {
		System.out.println("서버는 켜지냐1");
		SpringApplication.run(ImmigrationApplication.class, args);
		System.out.println("서버는 켜지냐2");
	}
}
