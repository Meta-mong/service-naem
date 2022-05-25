package com.metamong.metaticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MetaticketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetaticketApplication.class, args);
	}

}
