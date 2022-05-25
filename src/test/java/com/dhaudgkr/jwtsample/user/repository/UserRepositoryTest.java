package com.dhaudgkr.jwtsample.user.repository;

import com.dhaudgkr.jwtsample.domain.user.entity.Authority;
import com.dhaudgkr.jwtsample.domain.user.entity.User;
import com.dhaudgkr.jwtsample.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void findByUsername_테스트() {
        // given
        String username = "abcd1234";

        User saveUser = User.builder()
                .username(username)
                .password("12345")
                .activated(true)
                .build();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(saveUser));

        // when
        User user = userRepository.findByUsername(username).get();


        // then
        assertAll(
                () -> assertThat(user.getUsername()).isEqualTo(saveUser.getUsername()),
                () -> assertThat(user.getPassword()).isEqualTo(saveUser.getPassword())
        );
    }

    @Test
    void findOneWithAuthoritiesByUsername_테스트() {
        // given
        String username = "abcd1234";
        String saveAuthorityName = "ROLE_USER";

        Authority saveAuthority = Authority.builder()
                .authorityName(saveAuthorityName)
                .build();

        User saveUser = User.builder()
                .username(username)
                .password("12345")
                .activated(true)
                .authorities(Collections.singleton(saveAuthority))
                .build();

        when(userRepository.findOneWithAuthoritiesByUsername(any())).thenReturn(Optional.of(saveUser));

        // when
        User user = userRepository.findOneWithAuthoritiesByUsername(username).get();

        Iterator<Authority> iterator = user.getAuthorities().iterator();

        String authorityName = "";
        while (iterator.hasNext()) {
            Authority authority = iterator.next();
            authorityName = authority.getAuthorityName();
        }
        String finalAuthorityName = authorityName;

        // then
        assertAll(
                () -> assertThat(user.getUsername()).isEqualTo(saveUser.getUsername()),
                () -> assertThat(user.getPassword()).isEqualTo(saveUser.getPassword()),
                () -> assertThat(user.isActivated()).isEqualTo(saveUser.isActivated()),
                () -> assertThat(finalAuthorityName).isEqualTo(saveAuthorityName)
        );
    }
}
