package com.ecomerce.ms.service.inventory.application.command;

import com.ecomerce.ms.service.inventory.domain.shared.external.order.OrderItem;
import com.huyle.ms.command.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class UpdateInventoryOrderCommand implements Command {
    private List<OrderItem> orderItems;

    public Map<UUID, Integer> getProductMap() {
        return orderItems.stream()
                .collect(Collectors.toMap(OrderItem::getProductId, OrderItem::getQuantity));
    }

    public List<UUID> getProductIds() {
        return orderItems.stream()
                .map(OrderItem::getProductId)
                .collect(Collectors.toList());
    }
}
