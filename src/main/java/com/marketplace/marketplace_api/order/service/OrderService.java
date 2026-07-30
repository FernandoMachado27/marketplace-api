package com.marketplace.marketplace_api.order.service;

import com.marketplace.marketplace_api.auth.service.CurrentUserService;
import com.marketplace.marketplace_api.order.dto.OrderRequest;
import com.marketplace.marketplace_api.order.dto.OrderResponse;
import com.marketplace.marketplace_api.order.entity.Order;
import com.marketplace.marketplace_api.order.entity.OrderStatus;
import com.marketplace.marketplace_api.order.mapper.OrderMapper;
import com.marketplace.marketplace_api.order.orderitem.entity.OrderItem;
import com.marketplace.marketplace_api.order.repository.OrderRepository;
import com.marketplace.marketplace_api.product.entity.Product;
import com.marketplace.marketplace_api.product.service.ProductService;
import com.marketplace.marketplace_api.shared.exception.ResourceNotFoundException;
import com.marketplace.marketplace_api.user.entity.User;
import com.marketplace.marketplace_api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CurrentUserService currentUserService;

    public OrderResponse create(OrderRequest request) {
        User customer = currentUserService.getCurrentUser();

        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one product is required"
            );
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> items = new ArrayList<>();
        double totalPrice = 0.0;

        for (Map.Entry<Long, Integer> entry
                : request.getProducts().entrySet()) {

            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            if (quantity == null || quantity <= 0) {
                throw new IllegalArgumentException(
                        "Product quantity must be greater than 0 for productId: "
                                + productId
                );
            }

            Product product = productService.getById(productId);

            if (!Boolean.TRUE.equals(product.getActive())) {
                throw new ResourceNotFoundException(
                        "Active product not found with id: " + productId
                );
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setUnitPrice(product.getPrice());

            items.add(item);

            totalPrice += product.getPrice() * quantity;
        }

        order.setItems(items);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

    public OrderResponse pay(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if(!order.getActive()) {
            throw new IllegalStateException("Cannot pay a cancelled order");
        }

        order.setStatus(OrderStatus.PAID);
        Order saved = orderRepository.save(order);
        return orderMapper.toResponse(saved);
    }

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getMyOrders() {
        User customer = currentUserService.getCurrentUser();

        return orderRepository
                .findByCustomerIdOrderByCreatedAtDesc(customer.getId())
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    public void cancel(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setActive(false);
        orderRepository.save(order);
    }
}
