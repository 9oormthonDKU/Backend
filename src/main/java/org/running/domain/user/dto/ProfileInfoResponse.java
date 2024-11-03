package org.running.domain.user.dto;

import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;

@Getter
public class ProfileInfoResponse {
    private String name;
    private Integer age;
    private Integer targetDistance;

    public ProfileInfoResponse(String name, LocalDate birth, Integer targetDistance) {
        this.name = name;
        this.age = calculateAge(birth);
        this.targetDistance = targetDistance;
    }

    // 나이 계산 메서드
    private int calculateAge(LocalDate birth) {
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
