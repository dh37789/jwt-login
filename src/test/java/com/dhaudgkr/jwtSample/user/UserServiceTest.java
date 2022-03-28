package com.dhaudgkr.jwtSample.user;

import com.dhaudgkr.jwtSample.advice.exception.UsernameAlreadyExistsException;
import com.dhaudgkr.jwtSample.domain.dao.UserRepository;
import com.dhaudgkr.jwtSample.domain.entity.User;
import com.dhaudgkr.jwtSample.domain.service.UserService;
import com.dhaudgkr.jwtSample.web.dto.UserRegisterRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void create_회원가입_테스트() {
        // given
        final UserRegisterRequestDto dto = buildRegisterDtoRequest();
        given(userRepository.save(any(User.class))).willReturn(dto.toEntity());

        // when
        final User user = userService.registerUser(dto);

        // then
        verify(userRepository, atLeastOnce()).save(any(User.class));
        assertThatEqual(dto, user);
    }

    @Test
    void create_유저이름_중복_예외테스트() {
        // given
        final UserRegisterRequestDto dto = buildRegisterDtoRequest();
        given(userRepository.findByUsername(any())).willReturn(Optional.ofNullable(dto.toEntity()));

        // when
        Assertions.assertThrows(UsernameAlreadyExistsException.class, () ->{
            userService.registerUser(dto);
        });
    }

    private void assertThatEqual(UserRegisterRequestDto dto, User user) {
        assertThat(dto.getUsername(), is(user.getUsername()));
        assertThat(dto.getPassword(), is(user.getPassword()));
    }

    private UserRegisterRequestDto buildRegisterDtoRequest() {
        return UserRegisterRequestDto.builder()
                .username("dhaudgkr2")
                .password("1234")
                .build();
    }
}
