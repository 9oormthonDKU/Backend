package org.running.domain.user.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponse {
    private Long userId;
    private String email;
    private String name;
    private Integer gender;
    private LocalDate birth;
    private String location;
    private Integer distance;
}
