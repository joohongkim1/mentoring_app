package com.back.admin.service.jwt;

import com.back.admin.web.dto.user.UserJwtResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
public class JwtService {

    @Autowired
    private ObjectMapper objectMapper;

    private static final String SALT = "LastSecret";
    private static String key = "member";

    public UserJwtResponseDto getUser(String jwt){
        ObjectMapper mapper=new ObjectMapper();

        Jws<Claims> claims=null;
        try{
            claims=Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt);
        }catch (Exception e) {
            throw new UnauthorizedException();
        }

        //LinkedHashMap으로 변환되는 claims.getbody()을 객체화
        UserJwtResponseDto userJwtResponseDto =
                mapper.convertValue(claims.getBody().get(key), UserJwtResponseDto.class);

        return userJwtResponseDto;
    }


    // 토큰 발행(JWT 만들기)
    public <T> String create(T data) { // user
        String jwt =Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (log.isInfoEnabled()) {
                e.printStackTrace();
            } else {
                log.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }
        return key;
    }

    // claim으로 변환
    public boolean isUsable(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt); // 변환중
            return true; // 변환 완료 -> 유효한 토큰, true 반환
        } catch (Exception e) {
            throw new UnauthorizedException(); // 예외
        }
    }

    // JWT에 넣어 놓은 데이터를 가져온다.
    public Object get(String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SALT.getBytes("UTF-8"))
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
        return claims.getBody();
    }

}
