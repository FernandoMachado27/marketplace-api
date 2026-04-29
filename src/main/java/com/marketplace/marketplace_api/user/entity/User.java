package com.marketplace.marketplace_api.user.entity;

import com.marketplace.marketplace_api.user.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User { // Classe que representa uma tabela do banco

    @Id // chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // banco gera id automático
    private Long id;

    @Column(nullable = false, length = 150) // regras da coluna
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // salvar enum como string
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean active = true;

    @PrePersist // quando um usuário novo for salvo -- roda antes de inserir
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate // quando um usuário existente for atualizado -- roda antes de salvar
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
