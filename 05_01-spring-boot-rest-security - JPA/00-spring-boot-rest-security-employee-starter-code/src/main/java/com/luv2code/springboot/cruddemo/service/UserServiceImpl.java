package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserService{

    private UserDao userDao;

    private roleDao;
    @Override
    public User findByUserName(String userName) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
