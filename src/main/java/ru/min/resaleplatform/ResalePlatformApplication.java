package ru.min.resaleplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ResalePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResalePlatformApplication.class, args);
    }

}
