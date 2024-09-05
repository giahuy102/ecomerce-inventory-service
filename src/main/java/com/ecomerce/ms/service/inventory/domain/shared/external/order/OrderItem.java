package com.ecomerce.ms.service.inventory.domain.shared.external.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderItem {
    private UUID productId;
    private Integer quantity;
}
