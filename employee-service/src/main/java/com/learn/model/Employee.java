package com.learn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Employee")
public class Employee {

    @MongoId
    private  String id ;
    private String name ;
    private Address address;
}
