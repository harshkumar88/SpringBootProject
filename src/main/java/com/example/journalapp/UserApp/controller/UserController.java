package com.example.journalapp.UserApp.controller;

import com.example.journalapp.UserApp.DTO.UserEntry;
import com.example.journalapp.UserApp.service.UserEntryService;
import com.example.journalapp.DTO.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<List<UserEntry>> getAllUsers() {
        List<UserEntry> userEntries=userEntryService.getUserEntries();
        return new  ResponseEntity<>(userEntries, HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<UserEntry> getUserById(@PathVariable String username) {
        UserEntry userEntry=userEntryService.getUserEntry(username);
        return new  ResponseEntity<>(userEntry, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> addUser(@RequestBody UserEntry userEntry)  {
        UserEntry user= userEntryService.save(userEntry);
        String msg="User added successfully";
        boolean status=true;
        if(user==null) {
            msg="User not added";
            status=false;
        }
        Response response= Response.builder().message(msg).success(status).build();

        if(user!=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
