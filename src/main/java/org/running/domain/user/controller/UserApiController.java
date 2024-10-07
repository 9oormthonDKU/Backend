package org.running.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.LoginRequest;
import org.running.domain.user.dto.ProfileResponse;
import org.running.domain.user.dto.SignUpRequest;
import org.running.domain.user.dto.SignUpResponse;
import org.running.domain.user.dto.UpdateUserRequest;
import org.running.domain.user.model.User;
import org.running.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
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

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getUserProfile() {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // UserDetails 대신 User를 사용
        ProfileResponse responseDto = new ProfileResponse(user.getName(), user.getBirth(), user.getDistance());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UpdateUserRequest updateUserRequest) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // 서비스 레이어에서 유저 정보 업데이트 처리
        try {
            userService.patchUserProfile(user.getId(), updateUserRequest);
            return ResponseEntity.ok("유저 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 정보 수정 실패: " + e.getMessage());
        }
    }
}
