package com.ecom.app.Controller;

import com.ecom.app.Service.CartService;
import com.ecom.app.dto.CartItemDTO;
import com.ecom.app.dto.CartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request){
        return cartService.addToCart(userId, request) ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "Product Out of Stock or User not found"
                );
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
            ){
        return cartService.deleteFromCart(userId,productId) ?
                ResponseEntity.status(HttpStatus.OK).body("Product removed from cart") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCart(@RequestHeader("X-User-ID")String userId){
        return ResponseEntity.ok(cartService.getAllProducts(userId));
    }
}
