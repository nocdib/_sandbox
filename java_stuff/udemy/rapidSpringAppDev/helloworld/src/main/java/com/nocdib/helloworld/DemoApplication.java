package com.nocdib.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@SpringBootApplication
@RestController
public class DemoApplication {

	@RequestMapping(value="/", method=GET)
	public String helloGET(){
		return "Hello World (GET)";
	}

	@RequestMapping(value="/", method=POST)
	public String helloPOST(){
		return "Hello World (POST)";
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
