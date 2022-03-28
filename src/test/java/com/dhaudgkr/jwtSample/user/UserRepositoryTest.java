package com.dhaudgkr.jwtSample.user;

import com.dhaudgkr.jwtSample.domain.dao.UserRepository;
import com.dhaudgkr.jwtSample.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername_test(){
        final String username = "jns37789";
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        assertThat(user.getUsername()).isEqualTo(username);
    }


}
