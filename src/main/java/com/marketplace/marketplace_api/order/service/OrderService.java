package com.marketplace.marketplace_api.order.service;

import com.marketplace.marketplace_api.order.dto.OrderRequest;
import com.marketplace.marketplace_api.order.dto.OrderResponse;
import com.marketplace.marketplace_api.order.entity.Order;
import com.marketplace.marketplace_api.order.mapper.OrderMapper;
import com.marketplace.marketplace_api.order.repository.OrderRepository;
import com.marketplace.marketplace_api.product.entity.Product;
import com.marketplace.marketplace_api.product.service.ProductService;
import com.marketplace.marketplace_api.shared.exception.ResourceNotFoundException;
import com.marketplace.marketplace_api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderResponse create(OrderRequest request) {
        Order order = new Order();
        order.setCustomer(userService.findActiveUserById(request.getCustomerId()));

        List<Product> products = request.getProductIds().stream().map(productService::getById).toList();

        order.setProducts(products);
        Order saved = orderRepository.save(order);

        return orderMapper.toResponse(saved);
    }

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toResponse(order);
    }

    public void cancel(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setActive(false);
        orderRepository.save(order);
    }
}
