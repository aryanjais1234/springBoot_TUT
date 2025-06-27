package com.tcs.store.model;

import com.tcs.store.model.Users;
import jakarta.persistence.*;

@Table(name="Roles")
@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private
    Users user;

    public Roles(int roleId, String role, Users user) {
        this.roleId = roleId;
        this.role = role;
        this.user = user;
    }

    public Roles() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
