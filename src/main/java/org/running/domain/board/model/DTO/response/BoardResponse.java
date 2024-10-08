package org.running.domain.board.model.DTO.response;

import org.running.domain.board.model.DeleteStatus;
import org.running.domain.board.model.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BoardResponse {

    private Long boardNumber;
    private String title;
    private String content;
    private DeleteStatus deleteStatus;
    private List<ReplyResponse> reply;

    public static BoardResponse from(Board board){
        return new BoardResponse(
                board.getBoardNumber(),
                board.getTitle(),
                board.getContent(),
                board.getDeleteStatus(),
                board.getReply().stream().map(ReplyResponse::from).collect(Collectors.toList())
                //StreamAPI를 통해 주입-> reply에 있는 리스트를 해당 클래스의 리스트로 옮겨준다.
        );
    }

}
