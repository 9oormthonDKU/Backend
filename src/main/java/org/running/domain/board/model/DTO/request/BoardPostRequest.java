package org.running.domain.board.model.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardPostRequest {

    public BoardPostRequest() {
    }

    private String title;
    private String content;
    private String location;
    private LocalDateTime when_meet;
    private Integer limits;
    private Long distance;
    private Long pace;
}
