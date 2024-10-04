package org.running.domian.board.model.DTO.request;

import lombok.Data;

@Data
public class ReplyPostRequest {
    private Long boardNumber;
    private String content;
}
