package com.io.linkapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LinkAppApplication {

    public static void main(String[] args) {
        System.out.println("LinkApplication started");
        SpringApplication.run(LinkAppApplication.class, args);
    }

}
