package com.ecomerce.ms.service.inventory.infrastructure.rest;

import com.ecomerce.ms.service.inventory.infrastructure.rest.model.ProductRequest;
import com.ecomerce.ms.service.inventory.infrastructure.rest.model.ProductResponse;
import com.ecomerce.ms.service.inventory.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> getListProduct(
            @RequestParam(value = "num_page") Integer numPage) {
        return ResponseEntity.ok(productService.getListProduct(numPage));
    }

    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> insertProduct(
            @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.insertProduct(productRequest));
    }

    @PostMapping("/api/batch/products")
    public ResponseEntity<List<ProductResponse>> insertBatchCategories(
            @RequestBody List<ProductRequest> productRequests) {
        return ResponseEntity.ok(productService.insertBatchProduct(productRequests));
    }

    @PutMapping("/api/products")
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestBody ProductRequest productRequest,
            @RequestParam(value = "id") UUID productId) {
        return ResponseEntity.ok(productService.updateProduct(productRequest, productId));
    }
}
