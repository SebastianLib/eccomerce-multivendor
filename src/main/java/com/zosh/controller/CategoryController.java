package com.zosh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zosh.model.Category;
import com.zosh.request.CreateCategoryRequest;
import com.zosh.service.AuthService;
import com.zosh.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> createCategory(
    @RequestBody CreateCategoryRequest req,
    @RequestHeader("Authorization") String jwt
    ) throws Exception{
        authService.findUserByJwtToken(jwt);
        Category category = categoryService.createCategory(req);
         return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories(
    ) throws Exception {
        List<Category> category = categoryService.getAllCategories();
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @PathVariable long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        authService.findUserByJwtToken(jwt);
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

}
