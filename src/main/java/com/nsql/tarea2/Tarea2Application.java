package com.nsql.tarea2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


import java.util.List;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Tarea2Application {

	public static void main(String[] args) {
		SpringApplication.run(Tarea2Application.class, args);
	}
}