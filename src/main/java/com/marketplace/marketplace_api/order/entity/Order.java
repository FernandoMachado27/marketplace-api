package com.marketplace.marketplace_api.order.entity;

import com.marketplace.marketplace_api.order.orderitem.entity.OrderItem;
import com.marketplace.marketplace_api.product.entity.Product;
import com.marketplace.marketplace_api.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Qualquer pedido pertence a um cliente (ManyToOne)
    @ManyToOne(optional = false)
    private User customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private Double totalPrice = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

}
