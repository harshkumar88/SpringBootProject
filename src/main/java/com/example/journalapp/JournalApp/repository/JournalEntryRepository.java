package com.example.journalapp.JournalApp.repository;
import com.example.journalapp.JournalApp.DTO.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface JournalEntryRepository extends MongoRepository<JournalEntry,String> {

    List<JournalEntry> getJournalEntryById(String id);
}
