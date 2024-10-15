package org.running.domain.board.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class BoardModifyRequest {

    private Long boardNumber;
    private String title;
    private String content;

}
