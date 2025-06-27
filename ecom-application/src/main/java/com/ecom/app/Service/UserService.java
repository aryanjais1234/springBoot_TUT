package com.ecom.app.Service;

import com.ecom.app.Models.Address;
import com.ecom.app.Models.User;
import com.ecom.app.Repository.UserRepository;
import com.ecom.app.dto.AddressDTO;
import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user,userRequest);
        userRepository.save(user);
    }


    public Optional<UserResponse> fetchUser(Long id){
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest){
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser,updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName((user.getFirstName()));
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress()!=null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress()!=null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);
        }
    }
}
