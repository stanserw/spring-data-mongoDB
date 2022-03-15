package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("studentRepository")
public interface StudentRepository
        extends MongoRepository<Student, String> {

}
