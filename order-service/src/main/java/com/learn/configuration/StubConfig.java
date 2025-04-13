package com.learn.configuration;

import com.learn.EmployeeServiceGrpc;
import com.learn.productproto.ProductServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class StubConfig {

    //the channel name is mapped to the application.properties to find the location of the service
    @Bean
    EmployeeServiceGrpc.EmployeeServiceBlockingStub stub(GrpcChannelFactory channels){
        return EmployeeServiceGrpc.newBlockingStub(channels.createChannel("employeeService"));
    }

    @Bean
    ProductServiceGrpc.ProductServiceBlockingStub stubProduct(GrpcChannelFactory channels){
        return ProductServiceGrpc.newBlockingStub(channels.createChannel("productService"));
    }
}
