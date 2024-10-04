package org.running.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.SignUpRequest;
import org.running.domain.user.dto.SignUpResponse;
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
            .build();


        User savedUser = userRepository.save(user);

        return SignUpResponse.builder()
            .userId(savedUser.getId())
            .email(savedUser.getEmail())
            .name(savedUser.getUsername())
            .build();

    }
}
