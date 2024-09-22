package com.ecomerce.ms.service.inventory.infrastructure.kafka;

import com.ecomerce.ms.service.InventoryProcessingCommand;
import com.ecomerce.ms.service.InventoryProcessingReply;
import com.ecomerce.ms.service.OrderingSagaKey;
import com.ecomerce.ms.service.PaymentProcessingReply;
import com.ecomerce.ms.service.SagaStepStatusMessage;
import com.ecomerce.ms.service.inventory.application.command.CompensateInventoryOrderCommand;
import com.ecomerce.ms.service.inventory.application.command.UpdateInventoryOrderCommand;
import com.ecomerce.ms.service.inventory.domain.shared.external.order.OrderItem;
import com.huyle.ms.command.CommandGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.stream.Collectors;

import static com.ecomerce.ms.service.inventory.domain.shared.constant.Constants.INVENTORY_PROCESSING_COMPENSATION_TOPIC;
import static com.ecomerce.ms.service.inventory.domain.shared.constant.Constants.INVENTORY_PROCESSING_REPLY_TOPIC;
import static com.ecomerce.ms.service.inventory.domain.shared.constant.Constants.INVENTORY_PROCESSING_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryListener {

    private final CommandGateway commandGateway;
    private final KafkaTemplate<OrderingSagaKey, InventoryProcessingReply> inventoryProcessingTemplate;

    @KafkaListener(topics = INVENTORY_PROCESSING_TOPIC)
    public void onInventoryProcessingCommand(@Payload InventoryProcessingCommand inventoryProcessingCommand,
                                             @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) OrderingSagaKey sagaKey) {
        processInventoryEvent(inventoryProcessingCommand, sagaKey, false);
    }

    @KafkaListener(topics = INVENTORY_PROCESSING_COMPENSATION_TOPIC)
    public void onInventoryProcessingCompensationCommand(@Payload InventoryProcessingCommand inventoryProcessingCommand,
                                             @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) OrderingSagaKey sagaKey) {
        processInventoryEvent(inventoryProcessingCommand, sagaKey, true);
    }

    private void processInventoryEvent(InventoryProcessingCommand inventoryProcessingCommand, OrderingSagaKey sagaKey, boolean isCompensation) {
        List<OrderItem> orderItems = inventoryProcessingCommand.getOrder()
                .getOrderItems()
                .stream()
                .map(item -> OrderItem.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());
        SagaStepStatusMessage sagaStepStatusMessage;
        try {
            if (isCompensation) {
                commandGateway.handle(CompensateInventoryOrderCommand.builder()
                        .orderItems(orderItems)
                        .build());
                sagaStepStatusMessage = SagaStepStatusMessage.COMPENSATED;
            } else {
                commandGateway.handle(UpdateInventoryOrderCommand.builder()
                        .orderItems(orderItems)
                        .build());
                sagaStepStatusMessage = SagaStepStatusMessage.SUCCEEDED;
            }
        } catch (RuntimeException e) {
            sagaStepStatusMessage = isCompensation ? SagaStepStatusMessage.COMPENSATION_FAILED : SagaStepStatusMessage.FAILED;
        }
        var reply = InventoryProcessingReply.newBuilder()
                .setSagaMetadata(inventoryProcessingCommand.getSagaMetadata())
                .setSagaStepStatus(sagaStepStatusMessage)
                .build();
        inventoryProcessingTemplate.send(INVENTORY_PROCESSING_REPLY_TOPIC, sagaKey, reply)
                .addCallback(new ListenableFutureCallback<SendResult<OrderingSagaKey, InventoryProcessingReply>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Unable to send message=[\"{}\"] due to : {}", reply, ex.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<OrderingSagaKey, InventoryProcessingReply> result) {
                        log.info("Sent message=[\"{}\"] with offset=[\"{}\"]", reply, result.getRecordMetadata().offset());
                    }
                });
    }
}
