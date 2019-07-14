package com.lh.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ZuulServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServer1Application.class, args);
	}

}
