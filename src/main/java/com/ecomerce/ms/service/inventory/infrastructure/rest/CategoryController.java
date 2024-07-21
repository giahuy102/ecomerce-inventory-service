package com.ecomerce.ms.service.inventory.infrastructure.rest;

import com.ecomerce.ms.service.inventory.infrastructure.rest.model.CategoryRequest;
import com.ecomerce.ms.service.inventory.infrastructure.rest.model.CategoryResponse;
import com.ecomerce.ms.service.inventory.application.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponse>> getListCategory(
            @RequestParam(value = "num_page") Integer numPage) {
        return ResponseEntity.ok(categoryService.getListCategory(numPage));
    }

    @PostMapping("/api/categories")
    public ResponseEntity<CategoryResponse> insertCategory(
            @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.insertCategory(categoryRequest));
    }

    @PostMapping("/api/batch/categories")
    public ResponseEntity<List<CategoryResponse>> insertBatchCategories(
            @RequestBody List<CategoryRequest> categoryRequests) {
        return ResponseEntity.ok(categoryService.insertBatchCategory(categoryRequests));
    }

    @PutMapping("/api/categories")
    public ResponseEntity<CategoryResponse> updateCategory(
            @RequestBody CategoryRequest categoryRequest,
            @RequestParam(value = "id") UUID categoryId) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryRequest, categoryId));
    }
}
