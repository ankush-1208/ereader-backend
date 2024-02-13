package com.ankush.readapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException {

    private String statusCode;

    private String errorMessage;

    public BadRequestException(String errorMessage) {
        this.statusCode = HttpStatus.BAD_REQUEST.name();
        this.errorMessage = errorMessage;
    }
}
