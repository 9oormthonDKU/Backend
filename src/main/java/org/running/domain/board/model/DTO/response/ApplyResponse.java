package org.running.domain.board.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.running.domain.board.model.entity.Apply;
import org.running.domain.board.model.entity.Apply_posts;

@AllArgsConstructor
@Data
public class ApplyResponse {

    private Long Id;
    private Long userId;
    private Long BoardNumber;

    public static ApplyResponse from(Apply apply){
        Apply_posts applyPost = apply.getApply_posts().isEmpty() ? null : apply.getApply_posts().get(0);

        Long boardNumber = (applyPost != null && applyPost.getBoard() != null) ?
                applyPost.getBoard().getBoardNumber() : null;

        return new ApplyResponse(
                apply.getId(),
                apply.getUser().getId(),
                boardNumber);
    }
}
