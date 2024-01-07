package com.ecomerce.ms.service.inventory.service;

import com.ecomerce.ms.service.inventory.domain.Product;
import com.ecomerce.ms.service.inventory.repository.CategoryRepository;
import com.ecomerce.ms.service.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

//    @PersistenceContext
//    EntityManager em;

    @PersistenceUnit(name = "test")
    EntityManagerFactory emf;

    public void test() {
//        Product t = new Product();
//        t.setSkuNumber("12345678");
//        t.setCategory(categoryRepository.getReferenceById(UUID.fromString("6278181d-88e6-4377-8f65-448bc145f702")));
//        productRepository.save(t);
        System.out.println("=================================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Product m = em.find(Product.class, UUID.fromString("961f4ab9-18ef-4f50-8612-2d07a06a2a6f"));
        tx.commit();
//        em.close();
        //        em.close();
        //        em.clear();

        System.out.println(m.getCategory().getImageUrl());
    }
}
