package com.dhaudgkr.jwtsample.user.service;

import com.dhaudgkr.jwtsample.domain.user.dto.UserRegisterDto;
import com.dhaudgkr.jwtsample.domain.user.entity.Authority;
import com.dhaudgkr.jwtsample.domain.user.entity.User;
import com.dhaudgkr.jwtsample.domain.user.exception.UsernameAlreadyExistsException;
import com.dhaudgkr.jwtsample.domain.user.repository.UserRepository;
import com.dhaudgkr.jwtsample.domain.user.service.UserService;
import com.dhaudgkr.jwtsample.global.config.redis.RedisService;
import com.dhaudgkr.jwtsample.global.config.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisService redisService;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @InjectMocks
    private UserService userService;

    @Test
    void usernameAlreadyExistsException_테스트() {
        // given
        final UserRegisterDto.Request dto = buildSignUpDtoRequest();
        given(userRepository.findByUsername(any())).willReturn(Optional.of(dto.toEntity()));

        // when
        Assertions.assertThrows(UsernameAlreadyExistsException.class, () ->{
            userService.register(dto);
        });
    }

    @Test
    @Order(4)
    @DisplayName("회원정보 저장 테스트")
    void register_테스트() {
        // given
        final UserRegisterDto.Request dto = buildSignUpDtoRequest();
        given(userRepository.save(any(User.class))).willReturn(buildUser());

        // when
        final UserRegisterDto.Response user = userService.register(dto);

        // then
        verify(userRepository, atLeastOnce()).save(any(User.class));
        // then
        assertAll(
                () -> assertThat(user.getUsername()).isEqualTo(dto.getUsername())
        );
    }

    private User buildUser() {
        String saveAuthorityName = "ROLE_USER";

        Authority saveAuthority = Authority.builder()
                .authorityName(saveAuthorityName)
                .build();

        return User.builder()
                .id(1L)
                .username("abcd1234")
                .password("12345")
                .activated(true)
                .authorities(Collections.singleton(saveAuthority))
                .build();
    }

    private UserRegisterDto.Request buildSignUpDtoRequest() {
        return UserRegisterDto.Request.builder()
                .username("abcd1234")
                .password("12345")
                .build();
    }


}
