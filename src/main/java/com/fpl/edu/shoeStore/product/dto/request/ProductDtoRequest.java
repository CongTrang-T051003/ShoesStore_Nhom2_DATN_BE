package com.fpl.edu.shoeStore.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDtoRequest {
    private Long categoryId;
    private String name;
    private String slug;
    private String description;
    private String sku;
    private Double basePrice;
    private Boolean isActive;
}
