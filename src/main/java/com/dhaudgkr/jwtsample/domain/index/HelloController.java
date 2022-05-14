package com.dhaudgkr.jwtsample.domain.index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${api.v1}")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
