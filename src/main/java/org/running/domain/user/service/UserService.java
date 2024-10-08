package org.running.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.SignUpRequest;
import org.running.domain.user.dto.SignUpResponse;
import org.running.domain.user.dto.UpdateUserRequest;
import org.running.domain.user.model.User;
import org.running.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
