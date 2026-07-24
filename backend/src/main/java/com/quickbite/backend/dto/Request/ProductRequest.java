package com.quickbite.backend.dto.Request;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private Double price ;
    private String description;
}
