package com.marketplace.marketplace_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean // Spring cria e gerencia o objeto
    public PasswordEncoder passwordEncoder() { // Metodo que criptografa senhas
        return new BCryptPasswordEncoder();
    }

}
