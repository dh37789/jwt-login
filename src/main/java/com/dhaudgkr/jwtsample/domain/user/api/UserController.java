package com.dhaudgkr.jwtsample.domain.user.api;

import com.dhaudgkr.jwtsample.domain.user.dto.UserRegisterDto;
import com.dhaudgkr.jwtsample.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "${api.v1}/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto.Response> register(UserRegisterDto.Request requestDto) {
        return new ResponseEntity<>(userService.register(requestDto), HttpStatus.CREATED);
    }

}
