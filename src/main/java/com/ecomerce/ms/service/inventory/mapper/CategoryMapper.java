package com.ecomerce.ms.service.inventory.mapper;

import com.ecomerce.ms.service.inventory.domain.Category;
import com.ecomerce.ms.service.inventory.model.CategoryRequest;
import com.ecomerce.ms.service.inventory.model.CategoryResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryMapper {
    public Category toCategory(CategoryRequest categoryRequest);

    public CategoryResponse toCategoryResponse(Category categoryRecord);
}
