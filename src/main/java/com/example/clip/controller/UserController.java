package com.example.clip.controller;


import com.example.clip.services.PaymentService;
import com.example.clip.services.TransactionService;
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

    @Autowired
    TransactionService transactionService;

    @GetMapping("/payment")
    ResponseEntity payments() {
        return new ResponseEntity<>( paymentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/disbursement")
    ResponseEntity disbursement() {
        return new ResponseEntity<>( transactionService.disbursementAll(), HttpStatus.OK);
    }

    @GetMapping("/report")
    ResponseEntity report() {
        return new ResponseEntity<>( transactionService.report(), HttpStatus.OK);
    }

}
