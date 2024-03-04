package com.example.demo.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler;
    private AuthenticationFailureHandler loginFailHandler;

    @Autowired
    public void setLoginSuccessHandler(SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Autowired
    public void setLoginFailHandler(AuthenticationFailureHandler loginFailHandler) {
        this.loginFailHandler = loginFailHandler;
    }

    @Bean
    public SecurityFilterChain customFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrfConfig) -> csrfConfig.disable());

        http.authorizeHttpRequests( (authorizeRequests) ->
                authorizeRequests
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated()
        );

        http.formLogin( (login) ->
                login.loginPage("/login") // 로그인할 html 페이지의 url을 맵핑합니다.
                        .usernameParameter("id") // security 로그인 시 필요한 username 부분에 대한 파라미터를 설정합니다.
                        .passwordParameter("password") // security 로그인 시 필요한 password 부분에 대한 파라미터를 설정합니다.
                        .successHandler(loginSuccessHandler) // 로그인 이후 처리를 담당하기 위한 핸들러를 구현합니다.
                        .failureHandler(loginFailHandler) // 로그인 실패시 담당 핸들러
                        .permitAll()
                );




        return http.build();
    }
}
