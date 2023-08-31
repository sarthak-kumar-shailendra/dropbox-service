package com.dropbox.dropboxservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class DropboxServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DropboxServiceApplication.class, args);
	}

}
