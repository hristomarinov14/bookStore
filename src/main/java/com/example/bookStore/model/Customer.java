package com.example.bookStore.model;

import com.example.bookStore.dto.RegisterCustomerDto;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    private String phone;

    private String roles; // Comma-separated roles, e.g., "USER,ADMIN"

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public static Customer of(RegisterCustomerDto dto) {
        Customer c = new Customer();

        c.email = dto.getEmail();
        c.password = dto.getPassword();
        c.name = dto.getName();
        c.username = dto.getUsername();
        c.phone = dto.getPhone();

        return c;
    }

    // Encrypt the password before saving
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
