package com.example.journalapp.JournalApp.service;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class FormatTimeService {

    public String formatDate(Date date, String tFormat) {
        if (date == null || tFormat == null) {
            throw new IllegalArgumentException("Date or format string cannot be null");
        }

        // Convert Date to ZonedDateTime in UTC
        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneId.of("UTC"));

        // Format the date using the provided format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tFormat);
        return zonedDateTime.format(formatter);
    }

}