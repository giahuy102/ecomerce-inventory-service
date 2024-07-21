package com.ecomerce.ms.service.inventory.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {

    @JsonProperty(value = "product_id")
    private UUID id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty(value = "sku_number")
    private String skuNumber;

    @JsonProperty(value = "price_unit")
    private Double priceUnit;

    @JsonProperty(value = "quantity")
    private Integer quantity;

    @JsonProperty(value = "category_id")
    private UUID categoryId;
}
