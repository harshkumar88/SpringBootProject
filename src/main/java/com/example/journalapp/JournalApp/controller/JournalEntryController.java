package com.example.journalapp.JournalApp.controller;

import com.example.journalapp.Auth.Service.AuthService;
import com.example.journalapp.JournalApp.DTO.JournalEntry;
import com.example.journalapp.JournalApp.service.JournalEntryService;
import com.example.journalapp.DTO.ExceptionResponse;
import com.example.journalapp.DTO.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ExceptionResponse exceptionResponse;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(@RequestHeader HttpHeaders headers) throws Exception {
        String userName= authService.getUserName(headers);
        List<JournalEntry> journalEntries =journalEntryService.getJournalEntries(userName);
        System.out.println(journalEntries+"entries");
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createJournalEntry(@RequestHeader HttpHeaders headers,@RequestBody JournalEntry myjournalEntry) throws Exception {
        String userName= authService.getUserName(headers);
        journalEntryService.saveEntry(myjournalEntry,userName);
        Response res=Response.builder().message("Journal Entry Created").success(true).build();
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@RequestHeader HttpHeaders headers,@PathVariable String id) throws Exception {
        String userName= authService.getUserName(headers);
        JournalEntry journalEntry = journalEntryService.getJournalEntry(id,userName);
        if(journalEntry!=null){
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteJournalEntryById(@RequestHeader HttpHeaders headers,@PathVariable String id) throws Exception{
        String userName= authService.getUserName(headers);
        boolean is_deleted = journalEntryService.deleteJournalEntry(id,userName);
        String msg="Journal Deleted Successfully";
        if(!is_deleted){
            msg="Journal Entry Deletion Failed";
        }

        Response res=Response.builder().message(msg).success(is_deleted).build();

        if(is_deleted){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

    }


    @PutMapping("{id}")
    public ResponseEntity<Response>  updateJournalEntryById(@RequestHeader HttpHeaders headers,@PathVariable String id, @RequestBody JournalEntry myjournalEntry) throws Exception {
        String userName= authService.getUserName(headers);
        boolean is_updated = journalEntryService.updateJournalEntry(id,myjournalEntry,userName);
        String msg="Journal Updated Successfully";
        if(!is_updated){
            msg="Journal Entry Updation Failed";
        }

        Response res=Response.builder().message(msg).success(is_updated).build();

        if(is_updated){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

    }

}
