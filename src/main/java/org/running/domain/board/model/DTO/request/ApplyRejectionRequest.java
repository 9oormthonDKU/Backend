package org.running.domain.board.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRejectionRequest {

    private Long boardId;
    private Long ApplyId;
}
