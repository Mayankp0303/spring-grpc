package com.learn.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "order")
public class OrderDTO {

    @MongoId
    private String order_id;
    private String customer_id ;
    private String skuId ;
    private int qty;
    private String status ;
    private String originated ;

}
