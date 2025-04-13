package com.learn.repository;

import com.learn.model.ProductDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductDTO,String> {

    ProductDTO findBySkuId(String SkuId);
}
