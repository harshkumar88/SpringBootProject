package com.example.journalapp.Auth.repository;
import com.example.journalapp.Auth.Entity.AuthEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthRepository extends MongoRepository<AuthEntity, String > {
    AuthEntity findByToken(String token);
}
