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
@RestController
public class ImmigrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(ImmigrationApplication.class, args);
		System.out.println("이거 나오냐");
	}

	@GetMapping("/aaa")
	public Student getMapping() {
		return new Student("2417", "이진혁");
	}

	@PostMapping("/aaa")
	public Student postMapping(@RequestBody Student student) {
		student.setName("누군가");
		return student;
	}
}

class Student {
	private String number;
	private String name;

	public Student(String number, String name) {
		this.number = number;
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
