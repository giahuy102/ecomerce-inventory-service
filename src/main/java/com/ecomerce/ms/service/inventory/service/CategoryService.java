package com.ecomerce.ms.service.inventory.service;

import com.ecomerce.ms.service.inventory.domain.Category;
import com.ecomerce.ms.service.inventory.exception.DatabaseRecordNotFound;
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

import static com.ecomerce.ms.service.inventory.constant.MessageConstant.CATEGORY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse insertCategory(CategoryRequest categoryRequest) {
        UUID parentCategoryId = categoryRequest.getParentCategoryId();
        Category parentCategory = parentCategoryId != null ? categoryRepository.getReferenceById(parentCategoryId) : null;
        Category categoryRecord = categoryMapper.toCategory(categoryRequest);
        categoryRecord.setParentCategory(parentCategory);
        Category savedCategoryRecord = categoryRepository.save(categoryRecord);
        return buildCategoryResponse(savedCategoryRecord);
    }

    @Transactional
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, UUID categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DatabaseRecordNotFound(CATEGORY_NOT_FOUND));
        Category categoryRecord = categoryMapper.toCategory(categoryRequest);
        Category savedCategoryRecord = saveCategory(categoryRecord, categoryRequest.getParentCategoryId(), null);
        return buildCategoryResponse(savedCategoryRecord);
    }

    private Category saveCategory(Category categoryRecord, UUID parentCategoryId, UUID categoryId) {
        Category parentCategory = parentCategoryId != null ? categoryRepository.getReferenceById(parentCategoryId) : null;
        if (categoryId != null) {
            categoryRecord.setId(categoryId);
        }
        categoryRecord.setParentCategory(parentCategory);
        return categoryRepository.save(categoryRecord);
    }

    private CategoryResponse buildCategoryResponse(Category categoryRecord) {
        List<UUID> subCategoryIds = categoryRecord.getSubCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toCollection(ArrayList::new));
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(categoryRecord);
        Category parentCategoryRecord = categoryRecord.getParentCategory();
        categoryResponse.setParentCategoryId(parentCategoryRecord != null ? parentCategoryRecord.getId() : null);
        categoryResponse.setSubCategoryIds(subCategoryIds);
        return categoryResponse;
    }
}
