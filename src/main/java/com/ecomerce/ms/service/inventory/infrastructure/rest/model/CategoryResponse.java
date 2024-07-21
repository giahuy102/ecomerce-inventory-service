package com.ecomerce.ms.service.inventory.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {

    @JsonProperty("category_id")
    private UUID id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty(value = "parent_category_id")
    private UUID parentCategoryId;

    @JsonProperty(value = "sub_category_ids")
    private List<UUID> subCategoryIds;
}
