package com.project.filestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.project.filestorage")
public class FilestorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilestorageApplication.class, args);
	}

}
