package com.ecomerce.ms.service.inventory.infrastructure.mapper;

import com.ecomerce.ms.service.inventory.domain.aggregate.Category;
import com.ecomerce.ms.service.inventory.infrastructure.rest.model.CategoryRequest;
import com.ecomerce.ms.service.inventory.infrastructure.rest.model.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryMapper {
    public Category toCategory(CategoryRequest categoryRequest);
    public CategoryResponse toCategoryResponse(Category categoryRecord);
    public List<Category> toCategory(List<CategoryRequest> categoryRequests);
}
