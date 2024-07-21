package com.ecomerce.ms.service.inventory.infrastructure.mapper;

import com.ecomerce.ms.service.inventory.domain.aggregate.Product;
import com.ecomerce.ms.service.inventory.infrastructure.rest.model.ProductRequest;
import com.ecomerce.ms.service.inventory.infrastructure.rest.model.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductMapper {
    public Product toProduct(ProductRequest productRequest);
    public ProductResponse toProductResponse(Product productRecord);
    public List<Product> toProduct(List<ProductRequest> productRequests);
}
