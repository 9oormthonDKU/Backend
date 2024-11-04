package org.running.domain.board.model.DTO.response;


import org.running.domain.board.model.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ReplyResponse {
    private Long ReplyNumber;
    private String content;
    private String replier;


    public static ReplyResponse from(Reply reply){
        return new ReplyResponse(
                reply.getReplyNumber(),
                reply.getContent(),
                reply.getUser().getName());
    }
}
