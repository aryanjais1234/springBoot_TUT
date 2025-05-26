package com.spring.SpringSecEx.service;

import com.spring.SpringSecEx.model.UserPrincipal;
import com.spring.SpringSecEx.model.Users;
import com.spring.SpringSecEx.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of UserDetailsService for Spring Security authentication.
 * This service loads user-specific data and creates UserDetails objects for authentication.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    /**
     * Loads a user by username for authentication purposes.
     *
     * @param username the username to search for
     * @return UserDetails object containing user authentication information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Attempt to find user in database by username
        Users user = userRepo.findByUsername(username);

        // If user not found, log and throw authentication exception
        if (user == null) {
            System.out.println("User Not found");
            throw new UsernameNotFoundException("user not found");
        }

        // Convert domain user object to Spring Security UserDetails object
        return new UserPrincipal(user);
    }
}
