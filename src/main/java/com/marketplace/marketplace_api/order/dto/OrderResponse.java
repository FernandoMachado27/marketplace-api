package com.marketplace.marketplace_api.order.dto;

import com.marketplace.marketplace_api.order.entity.OrderStatus;
import com.marketplace.marketplace_api.order.orderitem.dto.OrderItemResponse;
import com.marketplace.marketplace_api.product.dto.ProductResponse;
import com.marketplace.marketplace_api.user.dto.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long id;
    private UserResponse customer;
    private List<OrderItemResponse> items;
    private Boolean active;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private Double totalPrice;
}
