package com.fpl.edu.shoeStore.order.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private int orderId;
    private int userId;
    private int buyerId;
    private Integer voucherId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String shippingFullname;
    private String shippingPhone;
    private String shippingAddress;
    private String shippingCity;
    private String shippingCountry;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
