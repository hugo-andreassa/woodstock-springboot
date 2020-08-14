package com.hyperdrive.woodstock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hyperdrive.woodstock.services.S3Service;

@SpringBootApplication
public class WoodstockApplication implements CommandLineRunner {

	@Autowired
	private S3Service service;
	
	public static void main(String[] args) {
		SpringApplication.run(WoodstockApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.uploadFile("logo.jpg");		
	}

}
