package com.ecomerce.ms.service.inventory.domain.shared.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String INVENTORY_PROCESSING_TOPIC = "inventory.processing.event";

    public static final String INVENTORY_PROCESSING_COMPENSATION_TOPIC = "inventory.processing.event.compensation";

    public static final String INVENTORY_PROCESSING_REPLY_TOPIC = "inventory.processing.reply";

}
