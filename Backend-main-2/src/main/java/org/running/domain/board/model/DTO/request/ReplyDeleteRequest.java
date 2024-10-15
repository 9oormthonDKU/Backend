package org.running.domain.board.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyDeleteRequest {
    private Long replyNumber;
}
