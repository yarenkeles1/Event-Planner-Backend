package com.yaren.eventplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventPlannerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventPlannerAppApplication.class, args);
    }

}
