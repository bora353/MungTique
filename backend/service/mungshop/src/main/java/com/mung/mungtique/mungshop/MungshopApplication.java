package com.mung.mungtique.mungshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MungshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MungshopApplication.class, args);
    }

}
