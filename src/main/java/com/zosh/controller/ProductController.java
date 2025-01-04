package com.zosh.controller;

import com.zosh.model.Product;
import com.zosh.request.CreateCategoryRequest;
import com.zosh.request.CreateProductRequest;
import com.zosh.response.ProductResponse;
import com.zosh.service.AuthService;
import com.zosh.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthService authService;

    @PostMapping()
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        authService.findUserByJwtToken(jwt);
        Product newProduct = productService.createProduct(req);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        authService.checkIfAdmin(jwt);
        ProductResponse allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<List<Product>> getAllProducts(
            @PathVariable Long categoryId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        authService.findUserByJwtToken(jwt);
        List<Product> allProducts = productService.getProductsByCategoryId(categoryId);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @PutMapping("{productId}")
    public ResponseEntity<Product> updateProduct(
            @RequestBody CreateProductRequest req,
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        authService.findUserByJwtToken(jwt);
        Product newProduct = productService.updateProduct(req, productId);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
}
