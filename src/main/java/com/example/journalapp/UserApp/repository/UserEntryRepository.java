package com.example.journalapp.UserApp.repository;
import com.example.journalapp.UserApp.DTO.UserEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<UserEntry, String> {
    UserEntry findByUsername(String username);
}
