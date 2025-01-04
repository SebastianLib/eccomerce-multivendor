package com.zosh.service;

import com.zosh.model.Product;
import com.zosh.request.CreateProductRequest;
import com.zosh.response.ProductResponse;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest req) throws Exception;

    ProductResponse getAllProducts() throws Exception;

    List<Product> getProductsByCategoryId(Long categoryId) throws Exception;

    Product updateProduct(CreateProductRequest req, Long productId) throws Exception;
}
