package org.running.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.LoginRequest;
import org.running.domain.user.dto.SignUpRequest;
import org.running.domain.user.dto.SignUpResponse;
import org.running.domain.user.dto.UpdateUserRequest;
import org.running.domain.user.model.User;
import org.running.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public SignUpResponse signUp(SignUpRequest signUpRequest) throws Exception{
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
            .email(signUpRequest.getEmail())
            .password(bCryptPasswordEncoder.encode(signUpRequest.getPassword()))
            .name(signUpRequest.getName())
            .gender(signUpRequest.getGender())
            .birth(signUpRequest.getBirth())
            .distance(signUpRequest.getDistance())
            .location(signUpRequest.getLocation())
            .build();


        User savedUser = userRepository.save(user);

        return SignUpResponse.builder()
            .userId(savedUser.getId())
            .email(savedUser.getEmail())
            .name(savedUser.getUsername())
            .build();
    }

    public String login(LoginRequest loginRequest, HttpServletRequest request) throws Exception {
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

        // 세션 생성 및 세션 ID 가져오기
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        return "로그인 성공: " + userDetails.getUsername() + ", 세션 ID: " + sessionId;
    }
    public void patchUserProfile(Long userId, UpdateUserRequest updateUserRequest) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("유저를 찾을 수 없습니다."));

        // 각 필드를 null 체크 후 수정
        if (updateUserRequest.getName() != null) {
            user.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getGender() != null) {
            user.setGender(updateUserRequest.getGender());
        }
        if (updateUserRequest.getBirth() != null) {
            user.setBirth(updateUserRequest.getBirth());
        }
        if (updateUserRequest.getDistance() != null) {
            user.setDistance(updateUserRequest.getDistance());
        }
        if (updateUserRequest.getLocation() != null) {
            user.setLocation(updateUserRequest.getLocation());
        }

        userRepository.save(user);
    }

}
