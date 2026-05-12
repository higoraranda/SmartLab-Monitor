package com.smartlab.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // ativa os @Scheduled — necessário para o scheduler de inatividade funcionar
public class SmartlabApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartlabApplication.class, args);
    }
}
