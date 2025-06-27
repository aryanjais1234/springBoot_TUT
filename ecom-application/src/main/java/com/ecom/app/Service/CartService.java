package com.ecom.app.Service;

import com.ecom.app.Models.CartItem;
import com.ecom.app.Models.Product;
import com.ecom.app.Models.User;
import com.ecom.app.Repository.CartItemRepository;
import com.ecom.app.Repository.ProductRepository;
import com.ecom.app.Repository.UserRepository;
import com.ecom.app.dto.CartItemDTO;
import com.ecom.app.dto.CartItemRequest;
import com.ecom.app.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    public boolean addToCart(String userId, CartItemRequest request) {

        Optional<Product> productOpt = productRepository.findById(request.getProductId());

        if(productOpt.isEmpty()) return false;

        Product product = productOpt.get();
        if(product.getStockQuantity()< request.getQuantity()) return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if(userOpt.isEmpty()) return false;

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);

        if(existingCartItem != null){
            // update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else{
            // create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productOpt.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItemDTO> getAllProducts(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(user-> cartItemRepository.findByUser(user).stream()
                        .map(item -> new CartItemDTO(item.getId(),item.getUser().getFirstName(), item.getProduct().getName(),item.getQuantity(),item.getPrice()))
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }
}
