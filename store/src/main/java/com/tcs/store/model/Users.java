package com.tcs.store.model;


import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Table(name="Users")
@Entity
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserId;

    private String userName;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Roles> roles = new ArrayList<>();


    public Users() {
    }



    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Roles> getRoles() {
         return roles;
    }

    public void setRoles(Set<Roles> roles) {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}
