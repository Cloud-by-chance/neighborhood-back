package com.example.nuvi_demo.config.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



//서버에 적용될 Spring Security의 보안을 설정하는 핵심 부분이다.
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){ //PasswordEncoder란  패스워드를 암호화 하는 방식이다. 평문 저장을 막기 위함

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Override //여기가 Spring 시큐리티 설정의 핵심
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                //예외 처리 핸들링
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                //세션을 사용하지 않기 위한 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로 세션필요없으므로 생성안함.
                .and() //and로 구분짓기.
                .authorizeRequests() // 아래의 리퀘스트에 대한 사용권한 체크,
                .antMatchers(AUTH_LIST).permitAll()  // AUTH_LIST에 해당되는 접근은 모두 허가해 준다.
                .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                .cors()
                //JWT 인증 필터 설정
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));

    }
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtEntryPoint jwtEntryPoint;
    private static final String[] AUTH_LIST={ //Security에서 허용해줄 url 설정
            "/v2/api-docs","/v3/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/webjars/**",
            "/swagger/**",
            "/swagger-ui/index.html",
            "/social/**",
            "/h2-console",
            "/",
            "/auth/logout/**",
            "/auth/login/**",
            "/auth/signup/**",
            "/auth/refreshtoken/**",
            "/auth/kakaoLogin/**",
            "/auth/kakaologin/**"
//            "/auth/validate"
    };



    @Override // 스웨거는 사용해야됨.
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**","/swagger-ui/index.html","/swagger-ui","/h2-console/**");

    }
}