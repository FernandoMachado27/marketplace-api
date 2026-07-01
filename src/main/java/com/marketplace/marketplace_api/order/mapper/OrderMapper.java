package com.marketplace.marketplace_api.order.mapper;

import com.marketplace.marketplace_api.order.dto.OrderResponse;
import com.marketplace.marketplace_api.order.entity.Order;
import com.marketplace.marketplace_api.product.service.ProductService;
import com.marketplace.marketplace_api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserService userService;
    private final ProductService productService;

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomer(userService.toResponse(order.getCustomer()));
        response.setProducts(order.getProducts().stream()
                .map(productService::toResponse)
                .toList());
        response.setActive(order.getActive());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }

}
