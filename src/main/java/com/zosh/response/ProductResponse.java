package com.zosh.response;

import com.zosh.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<Product> products;
    private Double totalCost;

    public ProductResponse(List<Product> products, Double totalCost) {
        this.products = products;
        this.totalCost = totalCost;
    }
}
