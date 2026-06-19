package com.marketplace.marketplace_api.product.mapper;

import com.marketplace.marketplace_api.product.dto.ProductResponse;
import com.marketplace.marketplace_api.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

}
