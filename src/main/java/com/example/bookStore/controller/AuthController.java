package com.example.bookStore.controller;

import com.example.bookStore.dto.LoginCustomerDto;
import com.example.bookStore.dto.RegisterCustomerDto;
import com.example.bookStore.dto.ResponseDto;
import com.example.bookStore.model.Customer;
import com.example.bookStore.model.response.LoginResponse;
import com.example.bookStore.repository.CustomerRepository;

import com.example.bookStore.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Customer>> register(@RequestBody RegisterCustomerDto customer) {
        var user = authService.signup(customer);
        return user.mapResult(cst -> ResponseEntity.ok(new ResponseDto<>(cst, null)),
                ex -> ResponseEntity.badRequest().body(new ResponseDto<>(null, ex.getMessage())));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(@RequestBody LoginCustomerDto customer, HttpSession session) {
        var user = authService.authenticate(customer, session);
        return user.mapResult(l -> ResponseEntity.ok(new ResponseDto<>(l, null)),
                ex -> ResponseEntity.badRequest().body(new ResponseDto<>(null, ex.getMessage())));
    }
}
