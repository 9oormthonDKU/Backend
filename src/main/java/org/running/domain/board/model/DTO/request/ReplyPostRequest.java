package org.running.domain.board.model.DTO.request;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.running.domain.user.model.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyPostRequest {
    private Long boardNumber;
    private String content;
    private User user;
}
