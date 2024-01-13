package com.ecomerce.ms.service.inventory.service;

import com.ecomerce.ms.service.inventory.domain.Product;
import com.ecomerce.ms.service.inventory.exception.DatabaseRecordNotFound;
import com.ecomerce.ms.service.inventory.mapper.ProductMapper;
import com.ecomerce.ms.service.inventory.model.ProductRequest;
import com.ecomerce.ms.service.inventory.model.ProductResponse;
import com.ecomerce.ms.service.inventory.repository.CategoryRepository;
import com.ecomerce.ms.service.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.ecomerce.ms.service.inventory.constant.MessageConstant.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse insertProduct(ProductRequest productRequest) {
        Product productRecord = productMapper.toProduct(productRequest);
        UUID categoryId = productRequest.getCategoryId();
        productRecord.setCategory(categoryRepository.getReferenceById(categoryId));
        Product savedProductRecord = productRepository.save(productRecord);
        ProductResponse productResponse = productMapper.toProductResponse(savedProductRecord);
        productResponse.setCategoryId(categoryId);
        return productResponse;
    }

    public ProductResponse updateProduct(ProductRequest productRequest, UUID productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new DatabaseRecordNotFound(PRODUCT_NOT_FOUND));
        Product productRecord = productMapper.toProduct(productRequest);
        UUID categoryId = productRequest.getCategoryId();
        productRecord.setCategory(categoryRepository.getReferenceById(categoryId));
        Product savedProductRecord = productRepository.save(productRecord);
        ProductResponse productResponse = productMapper.toProductResponse(savedProductRecord);
        productResponse.setCategoryId(categoryId);
        return productResponse;
    }
}
