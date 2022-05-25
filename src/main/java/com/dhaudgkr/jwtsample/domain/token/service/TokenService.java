package com.dhaudgkr.jwtsample.domain.token.service;

import com.dhaudgkr.jwtsample.domain.token.dto.TokenDto;
import com.dhaudgkr.jwtsample.domain.token.exception.InvalidRefreshTokenException;
import com.dhaudgkr.jwtsample.domain.token.exception.NotMatchedUserName;
import com.dhaudgkr.jwtsample.domain.token.exception.RefreshTokenNullException;
import com.dhaudgkr.jwtsample.global.config.common.Constants;
import com.dhaudgkr.jwtsample.global.config.redis.RedisService;
import com.dhaudgkr.jwtsample.global.config.security.jwt.JwtTokenProvider;
import com.dhaudgkr.jwtsample.global.config.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenService {

    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;
    private static long refreshTokenValidityInMilliseconds;

    public TokenService(RedisService redisService, JwtTokenProvider jwtTokenProvider,
                        @Value("${spring.jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInMilliseconds) {
        this.redisService = redisService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    public TokenDto reissue(String accessToken) {

        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        String username = authentication.getName();

        String refreshToken = redisService.getValues(Constants.REDIS_KEY.TOKEN_KEY + username);

        if (isEmptyRefreshToken(refreshToken)) {
            throw new RefreshTokenNullException();
        }

        if (!isValidRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException(refreshToken);
        }

        if (!isMatchedSubject(username, refreshToken)) {
            throw new NotMatchedUserName(refreshToken);
        }

        TokenDto tokenDto = jwtTokenProvider.issueToken(authentication);
        redisService.setValues(Constants.REDIS_KEY.TOKEN_KEY + username, tokenDto.getRefreshToken(), (int)(refreshTokenValidityInMilliseconds / 1000));

        return tokenDto;
    }

    private boolean isEmptyRefreshToken(String refreshToken) {
        return refreshToken.isEmpty();
    }

    private boolean isValidRefreshToken(String refreshToken) {
        return jwtTokenProvider.validateToken(refreshToken);
    }

    private boolean isMatchedSubject(String username, String refreshToken) {
        String usernameByRefreshToken = JwtUtils.getSubject(refreshToken);
        return username.equals(usernameByRefreshToken);
    }
}
