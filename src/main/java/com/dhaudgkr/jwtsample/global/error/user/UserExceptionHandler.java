package com.dhaudgkr.jwtsample.global.error.user;

import com.dhaudgkr.jwtsample.domain.user.exception.UserNotActivatedException;
import com.dhaudgkr.jwtsample.domain.user.exception.UserNotFoundException;
import com.dhaudgkr.jwtsample.domain.user.exception.UsernameAlreadyExistsException;
import com.dhaudgkr.jwtsample.global.error.ErrorCode;
import com.dhaudgkr.jwtsample.global.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> usernameAlreadyExistsException() {
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.USERNAME_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<ErrorResponse> userNotActivatedException() {
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.USER_NOT_ACTIVATED), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException() {
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.USER_NOT_FOUND), HttpStatus.BAD_REQUEST);
    }
}
