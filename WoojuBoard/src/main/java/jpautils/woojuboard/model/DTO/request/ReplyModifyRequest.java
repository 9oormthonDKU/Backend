package jpautils.woojuboard.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyModifyRequest {

    private Long replyNumber;
    private String content;
}
