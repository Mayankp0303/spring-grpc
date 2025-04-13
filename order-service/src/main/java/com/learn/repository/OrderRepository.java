package com.learn.repository;

import com.learn.model.OrderDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderDTO,String> {
}
