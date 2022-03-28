package com.dhaudgkr.jwtSample.config.security.jwt;

import com.dhaudgkr.jwtSample.domain.dao.UserRepository;
import com.dhaudgkr.jwtSample.domain.entity.User;
import com.dhaudgkr.jwtSample.web.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

//@Setter
@Component
public class JwtTokenProvider implements InitializingBean {

    private final String secretKey; /* token의 key */
    private final long accessTokenValidityInMilliseconds; /* access토큰의 만료시간 */
    private final long refreshTokenValidityInMilliseconds; /* refresh토큰의 만료시간 */

    public static final String USERNAME_KEY ="username";
    private Key key;

    private final UserRepository userRepository;

    public JwtTokenProvider(
            @Value("${spring.jwt.secret}") String secretKey,
            @Value("${spring.jwt.access-token-validity-in-seconds}") long accessTokenValidityInMilliseconds,
            @Value("${spring.jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInMilliseconds,
            UserRepository userRepository) {
        this.secretKey = secretKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
        this.userRepository = userRepository;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /*
     * Token 생성 메소드
     */
    public TokenDto createToken(String username){
        Long now = (new Date()).getTime();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        String accessToken = Jwts.builder()
                .claim("username", user.getUsername())
                .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("username", user.getUsername())
                .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);

        return (String) claims.get(USERNAME_KEY);
    }

    public Claims getClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
