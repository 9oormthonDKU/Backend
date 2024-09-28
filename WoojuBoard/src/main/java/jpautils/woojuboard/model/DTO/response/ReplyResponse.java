package jpautils.woojuboard.model.DTO.response;


import jpautils.woojuboard.model.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyResponse {
    private Long ReplyNumber;
    private String content;

    public static ReplyResponse from(Reply reply){
        return new ReplyResponse(
                reply.getReplyNumber(),
                reply.getContent());
    }


}
