package com.ecom.app.Controller;


import com.ecom.app.Models.User;
import com.ecom.app.Service.UserService;
import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return new ResponseEntity<>("User added successfully",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody UserRequest updatedUserRequest){
        return userService.updateUser(id,updatedUserRequest)
                ? ResponseEntity.ok("User updated successfully")
                : ResponseEntity.notFound().build();
    }
}
