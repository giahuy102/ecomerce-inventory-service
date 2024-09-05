package com.ecomerce.ms.service.inventory.application.command;

import com.ecomerce.ms.service.inventory.domain.aggregate.Product;
import com.ecomerce.ms.service.inventory.domain.aggregate.ProductRepository;
import com.huyle.ms.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CompensateInventoryOrderCommandHandler implements CommandHandler<CompensateInventoryOrderCommand, Void> {

    private final ProductRepository productRepository;

    @Override
    public Void handle(CompensateInventoryOrderCommand command) {
        List<UUID> productIds = command.getProductIds();
        Map<UUID, Integer> productToQuantity = command.getProductMap();
        List<Product> products = productRepository.findByIdIn(productIds);
        products.forEach(product -> {
            var productId = product.getId();
            Integer inStockQuantity = product.getQuantity();
            Integer demandQuantity = productToQuantity.get(productId);
            product.setQuantity(inStockQuantity + demandQuantity);
        });
        productRepository.saveAll(products);
        return null;
    }
}
