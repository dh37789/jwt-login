package com.dhaudgkr.jwtSample.advice;

import com.dhaudgkr.jwtSample.advice.exception.LoginFailException;
import com.dhaudgkr.jwtSample.advice.exception.UsernameAlreadyExistsException;
import com.dhaudgkr.jwtSample.domain.response.Response;
import com.dhaudgkr.jwtSample.domain.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    public ExceptionAdvice(ResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response usernameAlreadyExistsException() {
        final ErrorCode errorCode = ErrorCode.USERNAME_ALREADY_EXISTS;
        return responseService.getFailureResult(errorCode);
    }

    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response loginFailException() {
        final ErrorCode errorCode = ErrorCode.LOGIN_FAIL;
        return responseService.getFailureResult(errorCode);
    }
}
