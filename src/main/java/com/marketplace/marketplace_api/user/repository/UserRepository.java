package com.marketplace.marketplace_api.user.repository;

import com.marketplace.marketplace_api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // JpaRepository retorna operações prontas

    Optional<User> findByEmail(String email); // JPA entende o nome do metodo e realiza a query automática

    boolean existsByEmail(String email); // JPA verifica se já tem um usuário com este email
}
