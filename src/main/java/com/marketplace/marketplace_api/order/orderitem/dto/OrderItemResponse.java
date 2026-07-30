package com.marketplace.marketplace_api.order.orderitem.dto;

import com.marketplace.marketplace_api.product.dto.ProductResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse {

    private Long id;
    private ProductResponse product;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;

}
