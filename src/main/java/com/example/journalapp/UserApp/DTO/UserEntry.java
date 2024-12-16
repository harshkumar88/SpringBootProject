package com.example.journalapp.UserApp.DTO;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.example.journalapp.JournalApp.DTO.JournalEntry;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "user_entries")
@Data
@RequiredArgsConstructor
public class UserEntry {

    private String id;

    @Indexed(unique = true)
   @NonNull
    private String username;

   @NonNull
    private String email;
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries=new ArrayList<>();
    private boolean is_active=true;
}
