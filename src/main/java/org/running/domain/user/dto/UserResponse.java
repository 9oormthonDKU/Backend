package org.running.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.running.domain.user.model.User;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;

    public static UserResponse from(User user){
        return new UserResponse(
                user.getId());
    }
}