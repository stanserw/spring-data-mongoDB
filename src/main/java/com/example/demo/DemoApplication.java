package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
        return args -> {
            Address address = new Address(
                    "England",
                    "London",
                    "EN9"
            );

            String email = "jahmed@gmail.com";
            Student student = new Student(
                    "Jasmila",
                    "Ahmed",
                    email,
                    Gender.FEMALE,
                    address,
                    List.of("Computer Science", "Bicycle"),
                    BigDecimal.TEN,
                    LocalDateTime.now()
                    );

            repository.findStudentByEmail(email)
                    .ifPresentOrElse(s -> {
                        System.out.println(s + " already exists");
                    },() -> {
                        System.out.println("Inserting student " + student);
                        repository.insert(student);
                    });

            String email2 = "katarzyna.milosz@gmail.com";
            Student student2 = new Student(
                    "Katarzyna",
                    "Miłosz",
                    email2,
                    Gender.FEMALE,
                    address,
                    List.of("Jazda rowerem", "Ksiązki", "Podróże"),
                    BigDecimal.TEN,
                    LocalDateTime.now()
            );

            usingMongoTemplateAndQuery(repository, mongoTemplate, email2, student2);

            Optional<Student> studentByEmail = repository.findStudentByEmail(email2);
            Optional<Student> studentFindById = repository.findById(studentByEmail.get().getId());
            System.out.println("Find student by id " + studentFindById);


        };
    }

    private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        List<Student> students = mongoTemplate.find(query, Student.class);

        if (students.size() > 1) {
            throw new IllegalStateException(
                    "found many students with email " + email);
        }

        if (students.isEmpty()) {
            System.out.println("Inserting student " + student);
            repository.insert(student);
        } else {
            System.out.println(student + " already exists");
        }
    }

}
