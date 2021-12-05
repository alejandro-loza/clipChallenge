package com.example.clip.controller;


import com.example.clip.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/clip/user")
@RestController
public class UserController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/payment")
    ResponseEntity query() {
        return new ResponseEntity<>( paymentService.findAll(), HttpStatus.OK);
    }

}
