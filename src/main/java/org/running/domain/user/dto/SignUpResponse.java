package org.running.domain.user.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponse {
    private Long userId;
    private String email;
    private String name;
    private LocalDateTime birth;
    private String location;
    private Integer distance;
}
