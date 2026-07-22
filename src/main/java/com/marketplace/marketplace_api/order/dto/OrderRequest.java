package com.marketplace.marketplace_api.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrderRequest {
    // Map: productId -> quantidade
    private Map<Long, Integer> products;
}
