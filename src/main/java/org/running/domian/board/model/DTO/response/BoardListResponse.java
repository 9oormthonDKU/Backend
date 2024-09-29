package org.running.domian.board.model.DTO.response;

import org.running.domian.board.model.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardListResponse {
    private Long boardNumber;
    private String title;

    public static BoardListResponse from(Board board){
        return new BoardListResponse(
                board.getBoardNumber(),
                board.getTitle()
        );
    }
}
