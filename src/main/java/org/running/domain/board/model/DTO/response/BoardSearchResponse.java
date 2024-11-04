package org.running.domain.board.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.running.domain.board.model.entity.Board;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchResponse {
    private Long boardNumber;
    private String location;
    private LocalDateTime when_meet;
    private Long distance;
    private Long pace;

    public static BoardSearchResponse from(Board board) {
        return new BoardSearchResponse(
                board.getBoardNumber(),
                board.getLocation(),
                board.getWhen_meet(),
                board.getDistance(),
                board.getPace()
        );
    }
}
