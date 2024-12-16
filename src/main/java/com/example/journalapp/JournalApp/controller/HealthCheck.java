package com.example.journalapp.JournalApp.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    //convert automatically to json
    @GetMapping("/health-check")
    public  String healthCheck() {
        return "OK";
    }
}
