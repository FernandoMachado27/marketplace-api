package com.marketplace.marketplace_api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
