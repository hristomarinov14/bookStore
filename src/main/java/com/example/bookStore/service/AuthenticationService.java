package com.example.bookStore.service;

import com.example.bookStore.dto.LoginCustomerDto;
import com.example.bookStore.dto.RegisterCustomerDto;
import com.example.bookStore.exception.AuthenticationException;
import com.example.bookStore.model.Customer;
import com.example.bookStore.model.response.LoginResponse;
import com.example.bookStore.repository.CustomerRepository;

import com.example.bookStore.types.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            CustomerRepository customerRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Result<Customer, Exception> signup(RegisterCustomerDto input) {
        try {
            var customer = new Customer();

            customer.setName(input.getName());
            customer.setEmail(input.getEmail());
            customer.setPassword(passwordEncoder.encode(input.getPassword()));
            customer.setPhone(input.getPhone());
            customer.setUsername(input.getUsername());

            return Result.createWithValue(customerRepository.save(customer));
        } catch (Exception ex){
            // With this approach we are assuring there won't be race conditions
            return Result.createWithException(new AuthenticationException("Email or username already exists!"));
        }
    }

    public Result<LoginResponse, Exception> authenticate(LoginCustomerDto input, HttpSession session) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        session.setAttribute("SPRING_SECURITY_CONTEXT", auth);

        var customer = customerRepository.findByEmail(input.getEmail());

        if(customer.isEmpty()) {
            return Result.createWithException(new AuthenticationException("Wrong credentials or user doesn't exist."));
        }

        var dto = new LoginResponse();
        dto.setToken(session.getId());
        dto.setExpiresIn(session.getMaxInactiveInterval());

        return Result.createWithValue(dto);
    }
}
