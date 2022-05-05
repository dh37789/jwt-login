package com.dhaudgkr.jwtsample.domain.user.service;

import com.dhaudgkr.jwtsample.domain.user.dto.UserRegisterDto;
import com.dhaudgkr.jwtsample.domain.user.entity.User;
import com.dhaudgkr.jwtsample.domain.user.exception.UsernameAlreadyExistsException;
import com.dhaudgkr.jwtsample.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterDto.Response register(UserRegisterDto.Request requestDto) {
        if(isExistsUsername(requestDto.getUsername()))
            throw new UsernameAlreadyExistsException(requestDto.getUsername());

        requestDto.encodePassword(passwordEncoder.encode(requestDto.getPassword()));

        User user = userRepository.save(requestDto.toEntity());

        return UserRegisterDto.Response.builder()
                .username(user.getUsername())
                .build();
    }

    private boolean isExistsUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
