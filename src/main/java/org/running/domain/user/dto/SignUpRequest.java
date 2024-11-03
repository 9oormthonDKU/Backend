package org.running.domain.user.dto;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private Long userId;
    private String email;
    private String name;
    private String password;
    private Integer gender;
    private LocalDate birth;
    private String location;
    private Integer distance;
}
