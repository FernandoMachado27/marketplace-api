package com.marketplace.marketplace_api.product.controller;

import com.marketplace.marketplace_api.product.dto.ProductRequest;
import com.marketplace.marketplace_api.product.dto.ProductResponse;
import com.marketplace.marketplace_api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAllActive();
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivate(Long id) {
        productService.deactivate(id);
    }

}
