package com.dhaudgkr.jwtSample.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${api.v1}")
public class IndexController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
