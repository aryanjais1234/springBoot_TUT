package com.ecom.app.Controller;

import com.ecom.app.Service.Productservice;
import com.ecom.app.dto.ProductRequest;
import com.ecom.app.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final Productservice productservice;
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productservice.createProduct(productRequest),
                        HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productservice.getAllProducts());
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest){
        return productservice.updateProduct(productRequest,id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id){
        return productservice.deleteProduct(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam String keyword){
        return ResponseEntity.ok(productservice.searchProducts(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long id){
        return productservice.getProduct(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
