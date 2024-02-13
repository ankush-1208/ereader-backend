package com.ankush.readapp.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static Date getCurrentDate() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }
}
