package com.example.journalapp.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Response {
    private String message;
    private boolean success;
//    public Response(String message, boolean success) {
//        this.message = message;
//        this.success = success;
//    }

}
