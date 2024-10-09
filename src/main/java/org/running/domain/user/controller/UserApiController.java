package org.running.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.LoginRequest;
import org.running.domain.user.dto.ProfileResponse;
import org.running.domain.user.dto.SignUpRequest;
import org.running.domain.user.dto.SignUpResponse;
import org.running.domain.user.dto.UpdateUserRequest;
import org.running.domain.user.model.User;
import org.running.domain.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
        SignUpResponse response = userService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            // UserService로 로그인 로직을 위임
            String response = userService.login(loginRequest, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    @GetMapping("/profile") // 유저 정보 불러오기
    public ResponseEntity<ProfileResponse> getUserProfile(HttpServletRequest request) {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자 정보 로그 남기기
        logger.info("인증된 사용자: {}", authentication.getPrincipal());

        User user = (User) authentication.getPrincipal(); // UserDetails 대신 User를 사용
        ProfileResponse responseDto = new ProfileResponse(user.getName(), user.getBirth(), user.getDistance());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/profile") // 유저 정보 수정하기
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
