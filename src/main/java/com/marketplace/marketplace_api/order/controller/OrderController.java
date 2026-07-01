package com.marketplace.marketplace_api.order.controller;

import com.marketplace.marketplace_api.order.dto.OrderRequest;
import com.marketplace.marketplace_api.order.dto.OrderResponse;
import com.marketplace.marketplace_api.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController { // Pedido feito pelo comprador

    private final OrderService orderService;

    @PostMapping
    public OrderResponse create(@RequestBody OrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PatchMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        orderService.cancel(id);
    }

}
