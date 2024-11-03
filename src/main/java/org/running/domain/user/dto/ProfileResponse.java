package org.running.domain.user.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ProfileResponse {
    private String name;
    private Integer gender;
    private LocalDate birth;
    private Integer targetDistance;
    private String locate;

    public ProfileResponse(String name, Integer gender, LocalDate birth, Integer targetDistance, String locate) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.targetDistance = targetDistance;
        this.locate = locate;
    }
}
