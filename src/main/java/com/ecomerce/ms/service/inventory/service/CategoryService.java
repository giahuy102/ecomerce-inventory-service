package com.ecomerce.ms.service.inventory.service;

import com.ecomerce.ms.service.inventory.domain.Category;
import com.ecomerce.ms.service.inventory.exception.DatabaseRecordNotFound;
import com.ecomerce.ms.service.inventory.mapper.CategoryMapper;
import com.ecomerce.ms.service.inventory.model.CategoryRequest;
import com.ecomerce.ms.service.inventory.model.CategoryResponse;
import com.ecomerce.ms.service.inventory.repository.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ecomerce.ms.service.inventory.constant.MessageConstant.CATEGORY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getListCategory(Integer page) {
        int PAGE_SIZE = 20;
        List<Category> categoryRecords = categoryRepository.findAll(PageRequest.of(page, PAGE_SIZE)).getContent();
        return categoryRecords.stream()
                .map(this::buildCategoryResponse)
                .collect(Collectors.toList());
    }

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
    public List<CategoryResponse> insertBatchCategory(List<CategoryRequest> categoryRequests) {
        List<Category> categoryRecords = categoryMapper.toCategory(categoryRequests);
        List<Category> parentCategoryRefs = categoryRequests
                .stream()
                .map(request -> getCategoryRef(request.getParentCategoryId()))
                .collect(Collectors.toList());

        IntStream.range(0, parentCategoryRefs.size())
                .forEach(i -> categoryRecords.get(i).setParentCategory(parentCategoryRefs.get(i)));
        List<Category> savedCategoryRecords = categoryRepository.saveAll(categoryRecords);
        return savedCategoryRecords.stream()
                .map(this::buildCategoryResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, UUID categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DatabaseRecordNotFound(CATEGORY_NOT_FOUND));
        Category categoryRecord = categoryMapper.toCategory(categoryRequest);
        Category savedCategoryRecord = saveCategory(categoryRecord, categoryRequest.getParentCategoryId(), null);
        return buildCategoryResponse(savedCategoryRecord);
    }

    private Category getCategoryRef(UUID categoryId) {
        return categoryId != null ? categoryRepository.getReferenceById(categoryId) : null;
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
