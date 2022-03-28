package com.dhaudgkr.jwtSample.advice.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UsernameAlreadyExistsException extends RuntimeException {
    private final String username;

    @Builder
    public UsernameAlreadyExistsException(String username){
        this.username = username;
    }

    public UsernameAlreadyExistsException(String message, String username) {
        super(message);
        this.username = username;
    }

    public UsernameAlreadyExistsException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }
}
