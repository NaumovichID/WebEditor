package com.practice.webeditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebEditorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebEditorApplication.class, args);
	}

}
