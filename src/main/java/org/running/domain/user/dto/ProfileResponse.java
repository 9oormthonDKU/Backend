package org.running.domain.user.dto;

import java.time.LocalDate;
import java.time.Period;

public class ProfileResponse {
    private String name;
    private int age;
    private int targetDistance;

    public ProfileResponse(String name, LocalDate birth, int targetDistance) {
        this.name = name;
        this.age = calculateAge(birth);
        this.targetDistance = targetDistance;
    }

    // 나이 계산 메서드
    private int calculateAge(LocalDate birth) {
        return Period.between(birth, LocalDate.now()).getYears();
    }

    // Getter 메서드
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getTargetDistance() {
        return targetDistance;
    }
}
