package com.ecom.app.Service;

import com.ecom.app.Models.*;
import com.ecom.app.Repository.OrderRepository;
import com.ecom.app.Repository.UserRepository;
import com.ecom.app.dto.OrderItemDTO;
import com.ecom.app.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        // validate for cart items
        List<CartItem> cartItems = cartService.getCart(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
        // Validate for user

        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
            return Optional.empty();
        }
        User user = userOptional.get();
        // calculate total price

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                        .map(item -> new OrderItem(
                                null,
                                item.getProduct(),
                                item.getQuantity(),
                                item.getPrice(),
                                order
                        ))
                                .collect(Collectors.toList());
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        // clear the cart
        cartService.clearCart(userId);
        
        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(item-> new OrderItemDTO(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                        )).toList(),
                order.getCreatedAt()
        );
    }
}
