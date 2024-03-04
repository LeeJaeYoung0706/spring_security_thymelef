package com.example.demo.lib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 인증 프로바이더
 * 로그인시 사용자가 입력한 아이디와 비밀번호를 확인하고 해당 권한을 주는 클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsProvider implements AuthenticationProvider {

    private final RequestRestAPI restApi;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String id = authentication.getName();
        String password = authentication.getCredentials().toString();
        Map<String, Object> authMap = new HashMap<>();
        authMap.put("userId", id);
        authMap.put("password", password);

        try {
            Map auth = restApi.authRequestAPI(authMap);
            if(!auth.isEmpty()){
                String role = String.valueOf(auth.get("auth"));
                List<Map> authList = (List<Map>) auth.get("grantedAuth");
                String token = String.valueOf(auth.get("token"));
                List<SimpleGrantedAuthority> authority = authList.stream().map((v) -> new SimpleGrantedAuthority(v.get("authority").toString())).toList();

                if (authority.isEmpty()) {
                    throw new BadCredentialsException("에러에요 에러");
                }

                MemberDetails memberDetails = MemberDetails.of(id, role, token, authority);
                return new UsernamePasswordAuthenticationToken(memberDetails, null, authority);

            }
            throw new BadCredentialsException("에러에요 에러");
        } catch (IOException e) {
            throw new BadCredentialsException("에러에요 에러");
        } catch (NoSuchAlgorithmException e) {
            throw new BadCredentialsException("에러에요 에러");
        } catch (KeyManagementException e) {
            throw new BadCredentialsException("에러에요 에러");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
