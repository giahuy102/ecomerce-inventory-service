package com.ecomerce.ms.service.inventory.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products", schema = "inventory")
public class Product {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "sku_number")
    private String skuNumber;

    @Column(name = "price_unit")
    private Double priceUnit;

    @Column(name = "quantity")
    private Integer quantity;

}
