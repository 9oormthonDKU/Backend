package jpautils.woojuboard.model.DTO.request;

import jpautils.woojuboard.model.entity.Board;
import jpautils.woojuboard.model.entity.Reply;
import lombok.Data;

@Data
public class ReplyPostRequest {
    private Long boardNumber;
    private String content;
}
