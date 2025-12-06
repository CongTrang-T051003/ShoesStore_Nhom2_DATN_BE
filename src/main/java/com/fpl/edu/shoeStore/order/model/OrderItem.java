package com.fpl.edu.shoeStore.order.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int variantId;
    private String productNameSnapshot;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
