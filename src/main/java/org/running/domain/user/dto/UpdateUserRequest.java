package org.running.domain.user.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String name;
    private Integer gender;
    private LocalDate birth;
    private Integer distance;
    private String location;
}
