package com.ecomerce.ms.service.inventory.model;

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
public class CategoryRequest {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty(value = "parent_category_id")
    private UUID parentCategoryId;
}
