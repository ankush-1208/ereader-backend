package com.ankush.readapp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.runtime.ObjectMethods;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static Date getCurrentDate() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String getOrDefault(String s1, String s2) {
        return StringUtils.isNotBlank(s1) ? s1 : s2;
    }

    public static <T> T convertObject(Object sourceObject, Class<T> targetClass) {
        return objectMapper.convertValue(sourceObject, targetClass);
    }
}
