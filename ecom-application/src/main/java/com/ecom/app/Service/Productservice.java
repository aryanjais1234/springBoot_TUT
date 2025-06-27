package com.ecom.app.Service;

import com.ecom.app.Models.Product;
import com.ecom.app.Repository.ProductRepository;
import com.ecom.app.dto.ProductRequest;
import com.ecom.app.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Productservice {
    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }
    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
    public Optional<ProductResponse> updateProduct(ProductRequest productRequest, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct, productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setPrice(savedProduct.getPrice());
        response.setDescription(savedProduct.getDescription());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setCategory(savedProduct.getCategory());
        response.setActive(savedProduct.getActive());

        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> getProduct(Long id) {
        return productRepository.findById(id)
                .map(this::mapToProductResponse);
    }
}
