package com.marketplace.marketplace_api.shared.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) { // Recurso pedido não existe
        super(message);
    }

}
