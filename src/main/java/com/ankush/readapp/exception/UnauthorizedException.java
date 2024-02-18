package com.ankush.readapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnauthorizedException extends RuntimeException {

    private HttpStatus statusCode;

    private String errorMessage;

    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
        this.statusCode = HttpStatus.UNAUTHORIZED;
    }

}
