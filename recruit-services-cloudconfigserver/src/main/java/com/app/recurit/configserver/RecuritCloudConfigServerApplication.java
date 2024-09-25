package com.app.recurit.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableEncryptableProperties
@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication
public class RecuritCloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecuritCloudConfigServerApplication.class, args);
	}

}
