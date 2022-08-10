package com.thenuka.gocheetaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
@RestController
public class GocheetaprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GocheetaprojectApplication.class, args);
	}

	@GetMapping("/")
	public String heartbeat(HttpServletRequest request) {
		return String.format("Service up and running at: <b>%S</b>", request.getRequestURL().toString());
	}

	@GetMapping("/heartbeat")
	public String heartbeat() {
		return String.format("<h2>Service up and running</h2> </br> %S", LocalDateTime.now().getSecond());
	}

	@GetMapping("/secured")
	public String secured() {
		return "<h2>You are logged in</h2>";
	}

	@GetMapping("/error")
	public String error() {
		return "<h2>Oops something went wrong please contact support service...</h2>";
	}
}
