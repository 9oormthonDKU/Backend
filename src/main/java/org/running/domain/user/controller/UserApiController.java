package org.running.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.SignUpRequest;
import org.running.domain.user.dto.SignUpResponse;
import org.running.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("user")
public class UserApiController{

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
        SignUpResponse response = userService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }
}
