package com.ecomerce.ms.service.inventory.model;

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

    @JsonProperty(value = "parent_id")
    private UUID parentId;

    @JsonProperty(value = "childrenId")
    private List<UUID> childrenId;
}
