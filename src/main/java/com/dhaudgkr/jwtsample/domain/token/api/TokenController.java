package com.dhaudgkr.jwtsample.domain.token.api;

import com.dhaudgkr.jwtsample.domain.token.dto.TokenDto;
import com.dhaudgkr.jwtsample.domain.token.service.TokenService;
import com.dhaudgkr.jwtsample.global.config.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "${api.v1}/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDto> reissue(HttpServletRequest request) {
        String accessToken = JwtUtils.resolveToken(request);
        return new ResponseEntity<>(tokenService.reissue(accessToken), HttpStatus.OK);
    }

}
