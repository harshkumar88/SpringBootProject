package com.example.journalapp.Auth.controller;


import com.example.journalapp.Auth.Entity.AuthEntity;
import com.example.journalapp.Auth.Service.AuthService;
import com.example.journalapp.DTO.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Response> login(@RequestBody AuthEntity auth) {
        String token = authService.login(auth.getUserName());
        Response res= Response.builder().message(token).success(true).build();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }


}
