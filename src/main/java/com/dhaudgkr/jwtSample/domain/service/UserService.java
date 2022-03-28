package com.dhaudgkr.jwtSample.domain.service;

import com.dhaudgkr.jwtSample.advice.exception.LoginFailException;
import com.dhaudgkr.jwtSample.advice.exception.UsernameAlreadyExistsException;
import com.dhaudgkr.jwtSample.config.security.jwt.JwtTokenProvider;
import com.dhaudgkr.jwtSample.domain.dao.UserRepository;
import com.dhaudgkr.jwtSample.domain.dto.UserLoginDto;
import com.dhaudgkr.jwtSample.domain.entity.User;
import com.dhaudgkr.jwtSample.web.dto.UserLoginRequestDto;
import com.dhaudgkr.jwtSample.web.dto.UserRegisterRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegisterRequestDto requestDto) {
        if(isExistsUsername(requestDto.getUsername()))
            throw new UsernameAlreadyExistsException(requestDto.getUsername());

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public boolean isExistsUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public UserLoginDto loginUser(UserLoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(LoginFailException::new);
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
            throw new LoginFailException();
        return UserLoginDto.builder()
                .username(user.getUsername())
                .accessToken(jwtTokenProvider.createToken(user.getUsername()))
                .build();
    }
}
