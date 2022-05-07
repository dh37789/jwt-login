package com.dhaudgkr.jwtsample.global.config.security.jwt;

import com.dhaudgkr.jwtsample.domain.user.dto.TokenDto;
import com.dhaudgkr.jwtsample.global.config.common.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {

    private final String secretKey; /* token의 key */
    private static long accessTokenValidityInMilliseconds; /* access토큰의 만료시간 */
    private static long refreshTokenValidityInMilliseconds; /* refresh토큰의 만료시간 */

    private Key key;

    public JwtTokenProvider(
            @Value("${spring.jwt.secret}") String secretKey,
            @Value("${spring.jwt.access-token-validity-in-seconds}") long accessTokenValidityInMilliseconds,
            @Value("${spring.jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInMilliseconds) {
        this.secretKey = secretKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto issueToken(Authentication authentication, String username) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return TokenDto.builder()
                .accessToken(createAccessToken(username, authorities))
                .refreshToken(createRefreshToken(username,authorities))
                .build();
    }

    private String createAccessToken(String username, String authorities) {
        Date now = new Date();

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaim(username, authorities))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String createRefreshToken(String username, String authorities) {
        Date now = new Date();

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaim(username, authorities))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * jwtoken의 header생성
     * @return
     */
    private static Map<String, Object> createHeader() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");
        return headers;
    }

    /**
     * jwtToken의 claim생성
     * @param username
     * @return
     */
    private static Map<String, Object> createClaim(String username, String authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.USERNAME_KEY, username);
        claims.put(Constants.AUTHORITIES_KEY, authorities);
        return claims;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(Constants.AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return (String) claims.get(Constants.USERNAME_KEY);
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
            log.error("token expired" + e);
            return false;
        } catch (Exception e) {
            log.error("token exception" + e);
            return false;
        }
    }
}
