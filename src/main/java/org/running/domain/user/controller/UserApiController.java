package org.running.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.LoginRequest;
import org.running.domain.user.dto.SignUpRequest;
import org.running.domain.user.dto.SignUpResponse;
import org.running.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("user")
public class UserApiController{

    private final UserService userService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
        SignUpResponse response = userService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        try {
            // 이메일과 비밀번호를 이용해 인증 처리
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            // 인증이 성공하면 SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증된 사용자 정보 가져오기
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return ResponseEntity.ok("로그인 성공: " + userDetails.getUsername());
        } catch (Exception e) {
            // 인증 실패 시 401 Unauthorized 응답 반환
            return ResponseEntity.status(401).body("로그인 실패: " + e.getMessage());
        }
    }
}
