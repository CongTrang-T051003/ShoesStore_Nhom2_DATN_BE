package com.fpl.edu.shoeStore.product.convert;

import com.fpl.edu.shoeStore.product.entity.Product;
import com.fpl.edu.shoeStore.product.dto.request.ProductDtoRequest;
import com.fpl.edu.shoeStore.product.dto.response.ProductDtoResponse;

public class ProductConverter {
    public Product toEntity(ProductDtoRequest dto) {
        return Product.builder()
                .categoryId(dto.getCategoryId())
                .name(dto.getName())
                .slug(dto.getSlug())
                .description(dto.getDescription())
                .sku(dto.getSku())
                .basePrice(dto.getBasePrice())
                .isActive(dto.getIsActive())
                .build();
    }
    public ProductDtoResponse toResponse(Product entity) {
        return ProductDtoResponse.builder()
                .productId(entity.getProductId())
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .sku(entity.getSku())
                .basePrice(entity.getBasePrice())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
