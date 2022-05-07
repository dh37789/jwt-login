package com.dhaudgkr.jwtsample.domain.user.exception;

import lombok.Builder;

public class UserNotFoundException extends RuntimeException {
    private final String username;

    @Builder
    public UserNotFoundException(String username){
        this.username = username;
    }

    public UserNotFoundException(String message, String username) {
        super(message);
        this.username = username;
    }

    public UserNotFoundException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }
}
