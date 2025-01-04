package com.zosh.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zosh.model.Category;
import com.zosh.repository.CategoryRepository;
import com.zosh.request.CreateCategoryRequest;
import com.zosh.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CreateCategoryRequest req) throws Exception {
        if (req.getName() == null || req.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }

        List<Category> allCategories = categoryRepository.findAll();
        for (Category category : allCategories) {
            if (category.getName().equalsIgnoreCase(req.getName())) {
                throw new IllegalArgumentException("Category with this name already exists");
            }
        }

        Category category = new Category();
        category.setName(req.getName());

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() throws Exception {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long id) throws Exception {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }
}
