package com.example.journalapp.Auth.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@Document(collection = "auth_entries")
public class AuthEntity {

    private String token;
    @Indexed(unique = true)
    @NonNull
    private String userName;
}
