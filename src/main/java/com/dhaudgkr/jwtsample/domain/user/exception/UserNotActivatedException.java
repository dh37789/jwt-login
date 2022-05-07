package com.dhaudgkr.jwtsample.domain.user.exception;

import lombok.Builder;

public class UserNotActivatedException extends RuntimeException{
    private final String username;

    @Builder
    public UserNotActivatedException(String username){
        this.username = username;
    }

    public UserNotActivatedException(String message, String username) {
        super(message);
        this.username = username;
    }

    public UserNotActivatedException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }
}
