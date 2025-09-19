package com.purpleio.purpleio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.purpleio.purpleio")
public class PurpleIoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurpleIoApplication.class, args);
    }

}
