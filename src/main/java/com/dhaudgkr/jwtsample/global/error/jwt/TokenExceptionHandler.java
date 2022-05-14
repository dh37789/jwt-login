package com.dhaudgkr.jwtsample.global.error.jwt;

import com.dhaudgkr.jwtsample.global.config.security.jwt.exception.ExpireTokenException;
import com.dhaudgkr.jwtsample.global.config.security.jwt.exception.UnsupportedTokenException;
import com.dhaudgkr.jwtsample.global.error.ErrorCode;
import com.dhaudgkr.jwtsample.global.error.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(ExpireTokenException.class)
    public ResponseEntity<ErrorResponse> expireTokenException() {
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.TOKEN_EXPIRE), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnsupportedTokenException.class)
    public ResponseEntity<ErrorResponse> unsupportedTokenException() {
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.UNSUPPORTED_TOKEN), HttpStatus.FORBIDDEN);
    }
}
