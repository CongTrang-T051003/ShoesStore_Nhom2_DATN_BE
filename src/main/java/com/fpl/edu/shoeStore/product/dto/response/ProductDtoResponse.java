package com.fpl.edu.shoeStore.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDtoResponse {
    private Long productId;
    private Long categoryId;
    private String name;
    private String slug;
    private String description;
    private String sku;
    private Double basePrice;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
