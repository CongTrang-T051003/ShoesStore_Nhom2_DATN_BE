package com.fpl.edu.shoeStore.shipment.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Shipment {
    private int shipmentId;
    private int orderId;
    private String carrier;
    private String trackingNumber;
    private LocalDateTime shippedDate;
    private LocalDateTime deliveryDate;
    private String status;
    private BigDecimal shippingFee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
