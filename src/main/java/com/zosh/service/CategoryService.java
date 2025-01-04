package com.zosh.service;

import com.zosh.model.Category;
import com.zosh.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    Category createCategory(CreateCategoryRequest req) throws Exception;
    List<Category> getAllCategories() throws Exception;
    Category getCategoryById(long id) throws Exception;
}
