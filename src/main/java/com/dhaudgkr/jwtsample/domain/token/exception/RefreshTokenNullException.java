package com.dhaudgkr.jwtsample.domain.token.exception;

import lombok.Builder;

public class RefreshTokenNullException extends RuntimeException{

    @Builder
    public RefreshTokenNullException() {}

    public RefreshTokenNullException(String message) {
        super(message);
    }

    public RefreshTokenNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
