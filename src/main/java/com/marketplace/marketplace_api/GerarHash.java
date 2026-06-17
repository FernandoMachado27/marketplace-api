package com.marketplace.marketplace_api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GerarHash {
    public static void main(String[] args) {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("123456")); // Substitua "123456" pela senha desejada

    }
}
