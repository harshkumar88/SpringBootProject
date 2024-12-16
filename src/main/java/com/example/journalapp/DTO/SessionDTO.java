package com.example.journalapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDTO {
    @JsonProperty("user_context")
    UserContextDTO userContext;
    @JsonProperty("user_id")
    int crUserId;
    String name;
    String email;
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("refresh_token")
    String refreshToken;
}
