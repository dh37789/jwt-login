package com.dhaudgkr.jwtsample.domain.user.service;

import com.dhaudgkr.jwtsample.domain.user.dto.TokenDto;
import com.dhaudgkr.jwtsample.domain.user.dto.UserLoginDto;
import com.dhaudgkr.jwtsample.domain.user.dto.UserRegisterDto;
import com.dhaudgkr.jwtsample.domain.user.entity.User;
import com.dhaudgkr.jwtsample.domain.user.exception.UsernameAlreadyExistsException;
import com.dhaudgkr.jwtsample.domain.user.repository.UserRepository;
import com.dhaudgkr.jwtsample.global.config.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public UserRegisterDto.Response register(UserRegisterDto.Request requestDto) {
        if(isExistsUsername(requestDto.getUsername()))
            throw new UsernameAlreadyExistsException(requestDto.getUsername());

        requestDto.encodePassword(passwordEncoder.encode(requestDto.getPassword()));

        requestDto.setUserAuthority();

        User user = userRepository.save(requestDto.toEntity());

        return UserRegisterDto.Response.of(user);
    }

    private boolean isExistsUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public UserLoginDto.Response login(UserLoginDto.Request requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto token = jwtTokenProvider.issueToken(authentication);

        return UserLoginDto.Response.of(token);
    }
}
