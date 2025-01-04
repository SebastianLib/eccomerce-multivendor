package com.zosh.service.impl;

import com.zosh.model.Category;
import com.zosh.model.Product;
import com.zosh.repository.CategoryRepository;
import com.zosh.repository.ProductRepository;
import com.zosh.request.CreateProductRequest;
import com.zosh.response.ProductResponse;
import com.zosh.service.CategoryService;
import com.zosh.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req) throws Exception {
        Category category = categoryService.getCategoryById(req.getCategoryId());
        Product product = new Product();
        product.setCategory(category);
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        return productRepository.save(product);
    }

    @Override
    public ProductResponse getAllProducts() throws Exception {
        List<Product> products = productRepository.findAll();
        Double totalCost = productRepository.findTotalCost();
        return new ProductResponse(products, totalCost);
    }


    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) throws Exception {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product updateProduct(CreateProductRequest req, Long productId) throws Exception {
        // Znalezienie produktu na podstawie ID
        Optional<Product> productOptional = productRepository.findById(Math.toIntExact(productId));

        // Jeśli produkt nie istnieje, rzucenie wyjątku
        if (productOptional.isEmpty()) {
            throw new Exception("Product not found");
        }

        // Pobranie istniejącego produktu
        Product product = productOptional.get();

        // Aktualizacja danych produktu na podstawie CreateProductRequest
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());

        if (req.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(req.getCategoryId());
            if (categoryOptional.isEmpty()) {
                throw new Exception("Category not found");
            }
            product.setCategory(categoryOptional.get());
        }

        return productRepository.save(product);
    }

}
