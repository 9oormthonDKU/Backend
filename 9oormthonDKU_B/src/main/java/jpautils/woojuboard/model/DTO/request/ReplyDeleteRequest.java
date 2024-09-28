package jpautils.woojuboard.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyDeleteRequest {
    private Long replyNumber;
}
