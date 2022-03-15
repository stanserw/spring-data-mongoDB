package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository repository) {
        return args -> {
            Address address = new Address(
                    "England",
                    "London",
                    "EN9"
            );

            Student student = new Student(
                    "Jasmila",
                    "Ahmed",
                    "jahmed@gmail.com",
                    Gender.FEMALE,
                    address,
                    List.of("Computer Science", "Bicycle"),
                    BigDecimal.TEN,
                    LocalDateTime.now()
                    );

            repository.insert(student);
        };
    }

}
