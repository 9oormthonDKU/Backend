package org.running.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.running.domain.user.dto.AddUserRequest;
import org.running.domain.user.model.User;
import org.running.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        // User 엔티티를 생성하고 비밀번호를 인코딩하여 저장
        return userRepository.save(User.builder()
            .email(dto.getEmail())  // record의 getter는 필드명으로 사용
            .password(bCryptPasswordEncoder.encode(dto.getPassword())) // record의 필드 접근
            .build()).getId();
    }
}
