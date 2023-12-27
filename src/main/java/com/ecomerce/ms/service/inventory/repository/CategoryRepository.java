package com.ecomerce.ms.service.inventory.repository;

import com.ecomerce.ms.service.inventory.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
