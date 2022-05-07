package com.dhaudgkr.jwtsample.domain.user.api;

import com.dhaudgkr.jwtsample.domain.user.dto.UserLoginDto;
import com.dhaudgkr.jwtsample.domain.user.dto.UserRegisterDto;
import com.dhaudgkr.jwtsample.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.v1}/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserRegisterDto.Response> register(@RequestBody UserRegisterDto.Request requestDto) {
        return new ResponseEntity<>(userService.register(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserLoginDto.Response> login(@RequestBody UserLoginDto.Request requestDto) {
        return new ResponseEntity<>(userService.login(requestDto), HttpStatus.OK);
    }

}
