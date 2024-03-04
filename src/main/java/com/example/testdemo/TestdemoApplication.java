package com.example.testdemo;

import com.example.testdemo.artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication

public class TestdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestdemoApplication.class, args);
	}


	@Bean
	public IdWorker idWorker(){
		return new IdWorker(1 , 1 );
	}

}
