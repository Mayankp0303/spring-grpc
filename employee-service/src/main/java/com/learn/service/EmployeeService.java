package com.learn.service;

import com.learn.*;
import com.learn.repository.EmployeeRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Autowired
    private EmployeeRepository repository;


    @Override
    public void getAllEmployee(Empty request, StreamObserver<allEmployee> responseObserver) {
        super.getAllEmployee(request, responseObserver);
    }

    @Override
    public void getEmployeeById(EmployeeById request, StreamObserver<Employee> responseObserver) {
        Optional<com.learn.model.Employee> emp1 = repository.findById(request.getId());

        if(emp1.isEmpty()){
            responseObserver.onNext(Employee.newBuilder().setId("EMPTY").setName("EMPTY").build());
            responseObserver.onCompleted();
        }
        else{
            System.out.println(emp1.get().getId());
            com.learn.model.Address address = emp1.get().getAddress();
            responseObserver.onNext(Employee.newBuilder().setId(emp1.get().getId()).setName(emp1.get().getName()).setAddress(Address.newBuilder().setAddressLine1(emp1.get().getAddress().getAddressLine1()).setAddressLine2(emp1.get().getAddress().getAddressLine2()).setPostalcode(emp1.get().getAddress().getPostalcode())).build());
            responseObserver.onCompleted();
        }

    }




    @Override
    public void saveEmployee(Employee request, StreamObserver<Employee> responseObserver) {

        com.learn.model.Employee emp = new com.learn.model.Employee();
        System.out.println("Id :" +request.getId());
        System.out.println("Name :" +request.getName());
        System.out.println("Address :" +request.getAddress());
        emp.setAddress(com.learn.model.Address.builder().addressLine1(request.getAddress().getAddressLine1()).addressLine2(request.getAddress().getAddressLine2()).postalcode(request.getAddress().getPostalcode()).build());
        emp.setId(request.getId());
        emp.setName(request.getName());
        
        repository.save(emp);

        responseObserver.onNext(Employee.newBuilder().setId(emp.getId()).setName("SAVED").build());
        responseObserver.onCompleted();


    }
}

