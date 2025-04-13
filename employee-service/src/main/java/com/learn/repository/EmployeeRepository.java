package com.learn.repository;


import com.learn.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EmployeeRepository extends MongoRepository<Employee,String> {
}
