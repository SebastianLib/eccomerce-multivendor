package com.zosh.request;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Long categoryId;
}
