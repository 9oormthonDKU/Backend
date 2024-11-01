package org.running.domain.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico").permitAll() // 기본 페이지와 정적 리소스 모두 접근 가능
                .requestMatchers("/user/login","/user/signup").permitAll() // 로그인, 회원가입 누구나 접근 가능
                .requestMatchers("/user/profile","/user/info").hasAuthority("ROLE_USER")
                .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
            )

            .csrf(AbstractHttpConfigurer::disable
            )

            .formLogin(formLogin -> formLogin
                .defaultSuccessUrl("/user/profile", true) // 로그인 성공 후 리다이렉트할 경로
                .permitAll() // 로그인 페이지는 누구나 접근 가능
            )

            // 세션 정책을 IF_REQUIRED로 설정하여 필요할 때만 세션을 생성
            .sessionManagement((sessionManagement) -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 필요 시에만 세션 생성
            )
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 빈 등록을 통해서 암호화가 변경되었을 경우에 프로그램을 일일이 찾지 않고 ( 객체를 하나하나 찾지 않고 ) 빈에서 검색 후 변경
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
