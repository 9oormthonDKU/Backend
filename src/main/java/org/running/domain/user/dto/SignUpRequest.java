package org.running.domain.user.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private Long userId;
    private String email;
    private String name;
    private String password;
    private LocalDateTime birth;
    private String location;
    private Integer distance;
}
