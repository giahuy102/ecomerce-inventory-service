package com.ecomerce.ms.service.inventory.service;

import com.ecomerce.ms.service.inventory.domain.Category;
import com.ecomerce.ms.service.inventory.mapper.CategoryMapper;
import com.ecomerce.ms.service.inventory.model.CategoryRequest;
import com.ecomerce.ms.service.inventory.model.CategoryResponse;
import com.ecomerce.ms.service.inventory.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse insertCategory(CategoryRequest categoryRequest) {
        Category parentCategory = categoryRepository.getReferenceById(categoryRequest.getParentCategoryId());
        Category categoryRecord = categoryMapper.toCategory(categoryRequest);
        categoryRecord.setParentCategory(parentCategory);
        Category savedCategoryRecord = categoryRepository.saveAndFlush(categoryRecord);
        List<UUID> subCategoryIds = savedCategoryRecord.getSubCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toCollection(ArrayList::new));
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(savedCategoryRecord);
        Category parentCategoryRecord = savedCategoryRecord.getParentCategory();
        categoryResponse.setParentCategoryId(parentCategoryRecord != null ? parentCategoryRecord.getId() : null);
        categoryResponse.setSubCategoryIds(subCategoryIds);
        return categoryResponse;
    }
}
