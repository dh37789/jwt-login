package com.dhaudgkr.jwtSample.web.controller;

import com.dhaudgkr.jwtSample.domain.dto.UserLoginDto;
import com.dhaudgkr.jwtSample.domain.dto.UserRegisterDto;
import com.dhaudgkr.jwtSample.domain.response.SingleResponse;
import com.dhaudgkr.jwtSample.domain.service.ResponseService;
import com.dhaudgkr.jwtSample.domain.service.UserService;
import com.dhaudgkr.jwtSample.web.dto.UserLoginRequestDto;
import com.dhaudgkr.jwtSample.web.dto.UserRegisterRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${api.v1}/user")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    public UserController(UserService userService, ResponseService responseService){
        this.userService = userService;
        this.responseService = responseService;
    }

    @PostMapping("/register")
    public SingleResponse<UserRegisterDto> register(@RequestBody UserRegisterRequestDto requestDto) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        return responseService.getSingleResponse(userRegisterDto.toEntity(userService.registerUser(requestDto)));
    }

    @PostMapping("/login")
    public SingleResponse<UserLoginDto> login(@RequestBody UserLoginRequestDto requestDto) {
        return responseService.getSingleResponse(userService.loginUser(requestDto));
    }

}
