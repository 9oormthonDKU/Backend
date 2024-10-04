package org.running.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.AddUserRequest;
import org.running.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("user")
public class UserApiController{

    private final UserService userService;

    @PostMapping("/signup")
    public String Signup(AddUserRequest request){
        userService.save(request);
        return "회원가입 성공";
    }
}
