package com.ecomerce.ms.service.inventory.repository;

import com.ecomerce.ms.service.inventory.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
