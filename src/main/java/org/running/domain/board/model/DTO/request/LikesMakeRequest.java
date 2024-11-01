package org.running.domain.board.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikesMakeRequest {

    private Long Id;
    private Long board_Id;
    private Long user_Id;

}
