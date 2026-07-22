package com.marketplace.marketplace_api.order.service;

import com.marketplace.marketplace_api.auth.service.CurrentUserService;
import com.marketplace.marketplace_api.order.dto.OrderRequest;
import com.marketplace.marketplace_api.order.dto.OrderResponse;
import com.marketplace.marketplace_api.order.entity.Order;
import com.marketplace.marketplace_api.order.entity.OrderStatus;
import com.marketplace.marketplace_api.order.mapper.OrderMapper;
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
        // Valida usuário ativo
        User customer = currentUserService.getCurrentUser();

        // Valida se enviou produtos
        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            throw new IllegalArgumentException("No products provided for the order");
        }

        Order order = new Order();
        order.setCustomer(customer);

        List<Product> orderProducts = new ArrayList<>();
        double total = 0.0;

        // Percorre o map de produtos e quantidades
        for (Map.Entry<Long, Integer> entry : request.getProducts().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            if (quantity == null || quantity <= 0) {
                throw new IllegalArgumentException(
                        "Product quantity must be greater than 0 for productId: " + productId
                );
            }

            // Busca produto ativo
            Product product = productService.getById(productId);
            if (!product.getActive()) {
                throw new ResourceNotFoundException(
                        "Product inactive or not found: " + product.getName()
                );
            }

            // Adiciona a quantidade correta no pedido
            for (int i = 0; i < quantity; i++) {
                orderProducts.add(product);
                total += product.getPrice();
            }
        }

        // Seta produtos e total
        order.setProducts(orderProducts);
        order.setTotalPrice(total);

        // Salva pedido
        Order saved = orderRepository.save(order);

        // Retorna DTO usando mapper
        return orderMapper.toResponse(saved);
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
