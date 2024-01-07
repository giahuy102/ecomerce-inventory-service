package com.ecomerce.ms.service.inventory.controller;

import com.ecomerce.ms.service.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/test")
    public String test() {
        productService.test();
        return "Successfully testing";
    }

}
