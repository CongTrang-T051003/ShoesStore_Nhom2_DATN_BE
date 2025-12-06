package com.fpl.edu.shoeStore.promotion.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Voucher {
    private int voucherId;
    private String code;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private int maxUses;
    private int usedCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;
    private LocalDateTime createdAt;
}
