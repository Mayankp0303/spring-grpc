package com.learn.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String postalcode;
}
