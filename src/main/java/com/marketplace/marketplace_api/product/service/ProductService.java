package com.marketplace.marketplace_api.product.service;

import com.marketplace.marketplace_api.product.mapper.ProductMapper;
import com.marketplace.marketplace_api.product.dto.ProductRequest;
import com.marketplace.marketplace_api.product.dto.ProductResponse;
import com.marketplace.marketplace_api.product.entity.Product;
import com.marketplace.marketplace_api.product.repository.ProductRepository;
import com.marketplace.marketplace_api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());

        Product saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }

    public List<ProductResponse> getAllActive() {
        List<Product> products = productRepository.findByActiveTrue();
        return products.stream().map(productMapper::toResponse).toList();
    }

    public void deactivate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Active user not found with id: " + id));
        product.setActive(false);
        productRepository.save(product);
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public ProductResponse toResponse(Product product) {
        return productMapper.toResponse(product);
    }
}
