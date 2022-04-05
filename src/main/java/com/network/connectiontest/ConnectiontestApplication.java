package com.network.connectiontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConnectiontestApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.debug","all");
		SpringApplication.run(ConnectiontestApplication.class, args);
	}

}
