package com.ecomerce.ms.service.inventory.service;

import com.ecomerce.ms.service.inventory.domain.Category;
import com.ecomerce.ms.service.inventory.domain.Product;
import com.ecomerce.ms.service.inventory.exception.DatabaseRecordNotFound;
import com.ecomerce.ms.service.inventory.mapper.ProductMapper;
import com.ecomerce.ms.service.inventory.model.ProductRequest;
import com.ecomerce.ms.service.inventory.model.ProductResponse;
import com.ecomerce.ms.service.inventory.repository.CategoryRepository;
import com.ecomerce.ms.service.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ecomerce.ms.service.inventory.constant.ApplicationConfigConstant.PRODUCT_PAGE_SIZE;
import static com.ecomerce.ms.service.inventory.constant.MessageConstant.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public List<ProductResponse> getListProduct(int pageNum) {
        List<Product> productRecords = productRepository.findAll(PageRequest.of(pageNum, PRODUCT_PAGE_SIZE)).getContent();
        return productRecords.stream()
                .map(this::buildProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse getProductById(UUID productId) {
        Product record = productRepository.findById(productId)
                .orElseThrow(() -> new DatabaseRecordNotFound(PRODUCT_NOT_FOUND));
        return buildProductResponse(record);
    }

    @Transactional
    public ProductResponse insertProduct(ProductRequest productRequest) {
        Product productRecord = productMapper.toProduct(productRequest);
        return buildProductResponse(saveProduct(productRecord, productRequest.getCategoryId()));
    }

    @Transactional
    public ProductResponse updateProduct(ProductRequest productRequest, UUID productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new DatabaseRecordNotFound(PRODUCT_NOT_FOUND));
        Product productRecord = productMapper.toProduct(productRequest);
        return buildProductResponse(saveProduct(productRecord, productRequest.getCategoryId()));
    }

    public List<ProductResponse> insertBatchProduct(List<ProductRequest> productRequests) {
        List<Product> productRecords = productMapper.toProduct(productRequests);
        List<Product> savedProductRecords = saveBatchProduct(productRecords, productRequests.stream()
                .map(ProductRequest::getCategoryId)
                .collect(Collectors.toList()));
        return savedProductRecords.stream()
                .map(this::buildProductResponse)
                .collect(Collectors.toList());
    }

    private Product saveProduct(Product productRecord, UUID categoryId) {
        productRecord.setCategory(getCategoryRef(categoryId));
        return productRepository.save(productRecord);
    }

    private List<Product> saveBatchProduct(List<Product> productRecords, List<UUID> categoryIds) {
        IntStream.range(0, productRecords.size())
                .forEach(i -> productRecords.get(i)
                        .setCategory(getCategoryRef(categoryIds.get(i))));
        return productRepository.saveAll(productRecords);
    }

    private ProductResponse buildProductResponse(Product productRecord) {
        ProductResponse productResponse = productMapper.toProductResponse(productRecord);
        productResponse.setCategoryId(productRecord.getCategory().getId());
        return productResponse;
    }

    private Category getCategoryRef(UUID categoryId) {
        return categoryId != null ? categoryRepository.getReferenceById(categoryId) : null;
    }
}
