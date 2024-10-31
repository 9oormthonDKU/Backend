package org.running.domain.board.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyModifyRequest {

    private Long replyNumber;
    private String content;
}
