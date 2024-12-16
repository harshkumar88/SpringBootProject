package com.example.journalapp.UserApp.service;
import com.example.journalapp.UserApp.DTO.UserEntry;
import com.example.journalapp.UserApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    public List<UserEntry> getUserEntries() {
        return userEntryRepository.findAll();
    }

    public UserEntry getUserEntry(String username) {
        return userEntryRepository.findByUsername(username);
    }

    public UserEntry save(UserEntry userEntry) {

            return userEntryRepository.save(userEntry);

    }

}

