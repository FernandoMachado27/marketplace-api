package com.marketplace.marketplace_api.order.orderitem.mapper;

import com.marketplace.marketplace_api.order.orderitem.dto.OrderItemResponse;
import com.marketplace.marketplace_api.order.orderitem.entity.OrderItem;
import com.marketplace.marketplace_api.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {

    private final ProductMapper productMapper;

    public OrderItemResponse toResponse(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();

        response.setId(item.getId());
        response.setProduct(productMapper.toResponse(item.getProduct()));
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setSubtotal(item.getUnitPrice() * item.getQuantity());

        return response;
    }

}
