package com.example.journalapp.filters.auth;

import com.example.journalapp.Auth.Service.AuthService;
import com.example.journalapp.UserApp.service.UserEntryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import com.example.journalapp.DTO.Response;
import com.example.journalapp.DTO.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthFilter extends OncePerRequestFilter {


    @Autowired
    AuthService authService;

    @Value("${spring.public.urls}")
    String publicUrls;

    List<String> publicUrlList;


//    @Autowired
//    RestCallService restCallService;

    @PostConstruct
    public void init() {
        publicUrlList = Arrays.asList(publicUrls.split(","));
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("access-token");
        String requestURI = request.getRequestURI();
        boolean isPublicUrl = false;
        for (String url : publicUrlList) {
            if (requestURI.contains(url)) {
                isPublicUrl = true;
                break;
            }
        }
        if (!isPublicUrl) {
            if (accessToken == null || accessToken.isEmpty()) {
                makeErrorResponse(response, 401, "Access-Token not found !!");
            } else {
                boolean sessionDTO  = authService.authenticateUser(accessToken);
                if (sessionDTO) {
                    request.setAttribute("sessionDTO", sessionDTO);
                    filterChain.doFilter(request, response);
                } else {
                    makeErrorResponse(response, 401, "Authentication failed !!");
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void makeErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(convertObjectToJson(Response.builder().message(message).success(false).build()));
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
