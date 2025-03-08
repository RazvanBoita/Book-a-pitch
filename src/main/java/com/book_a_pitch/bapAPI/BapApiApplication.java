package com.book_a_pitch.bapAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/api")
public class BapApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BapApiApplication.class, args);
	}

}
