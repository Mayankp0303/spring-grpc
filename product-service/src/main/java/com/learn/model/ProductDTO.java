package com.learn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "product")
public class ProductDTO {

    @MongoId
    private String id;
    private String skuId;
    private String price ;
    private boolean avail;
    private int qty;
}
