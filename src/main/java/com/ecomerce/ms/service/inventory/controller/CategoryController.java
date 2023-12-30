package com.ecomerce.ms.service.inventory.controller;

import com.ecomerce.ms.service.inventory.model.CategoryRequest;
import com.ecomerce.ms.service.inventory.model.CategoryResponse;
import com.ecomerce.ms.service.inventory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/categories")
    public ResponseEntity<CategoryResponse> insertCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.insertCategory(categoryRequest));
    }
}
