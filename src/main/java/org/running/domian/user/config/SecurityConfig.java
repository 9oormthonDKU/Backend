package org.running.domian.user.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.running.domian.user.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico").permitAll() // 기본 페이지와 정적 리소스 모두 접근 가능
                .requestMatchers("/api/user/signup/basic").permitAll() // 회원가입 접근 가능
                .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
            )

            .csrf(AbstractHttpConfigurer::disable
            )

            .formLogin((formLogin) -> formLogin
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true) // 세션 초기화
            )

            // 세션 정책을 IF_REQUIRED로 설정하여 필요할 때만 세션을 생성
            .sessionManagement((sessionManagement) -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요 시에만 세션 생성
            )
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 빈 등록을 통해서 암호화가 변경되었을 경우에 프로그램을 일일이 찾지 않고 ( 객체를 하나하나 찾지 않고 ) 빈에서 검색 후 변경
    }

    @Bean
    AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception { // AuthenticationManager은 스프링 시큐리티의 인증을 처리하는 클래스
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
