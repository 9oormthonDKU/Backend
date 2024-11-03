package org.running.domain.board.model.DTO.response;

import org.running.domain.board.model.DeleteStatus;
import org.running.domain.board.model.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardResponse {

    private Long boardNumber;
    private String title;
    private String content;
    private String location;
    private LocalDateTime when_meet;
    private Integer limits;
    private Long distance;
    private Long pace;
    private DeleteStatus deleteStatus;
    private List<ReplyResponse> reply;
    private Integer statement;


    public static BoardResponse from(Board board){
        return new BoardResponse(
                board.getBoardNumber(),
                board.getTitle(),
                board.getContent(),
                board.getLocation(),
                board.getWhen_meet(),
                board.getLimits(),
                board.getDistance(),
                board.getPace(),
                board.getDeleteStatus(),
                board.getReply().stream().map(ReplyResponse::from).collect(Collectors.toList()),
                board.getStatement()
                //StreamAPI를 통해 주입-> reply에 있는 리스트를 해당 클래스의 리스트로 옮겨준다.
        );
    }

}
