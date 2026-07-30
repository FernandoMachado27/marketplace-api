package com.marketplace.marketplace_api.order.mapper;

import com.marketplace.marketplace_api.order.dto.OrderResponse;
import com.marketplace.marketplace_api.order.entity.Order;
import com.marketplace.marketplace_api.order.orderitem.mapper.OrderItemMapper;
import com.marketplace.marketplace_api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserService userService;
    private final OrderItemMapper orderItemMapper;

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomer(userService.toResponse(order.getCustomer()));
        response.setItems(
                order.getItems()
                        .stream()
                        .map(orderItemMapper::toResponse)
                        .toList()
        );
        response.setTotalPrice(order.getTotalPrice());
        response.setActive(order.getActive());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}
