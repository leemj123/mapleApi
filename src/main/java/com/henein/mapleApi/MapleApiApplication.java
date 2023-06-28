package com.henein.mapleApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MapleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapleApiApplication.class, args);
		System.out.print("============start================================");
	}


}
