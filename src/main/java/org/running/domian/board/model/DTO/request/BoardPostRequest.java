package org.running.domian.board.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardPostRequest {
    private String title;
    private String content;

}
