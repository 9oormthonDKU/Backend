package org.running.domain.board.model.DTO.response;

import org.running.domain.board.model.entity.Board;
import org.running.domain.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMyResponse {

    private Long userId;
    private Long boardNumber;

    public static BoardMyResponse from(Board board) {
        return new BoardMyResponse(
                board.getUser().getId(),  // Board에서 User ID 가져오기
                board.getBoardNumber()    // Board 번호 가져오기
        );
    }
}
