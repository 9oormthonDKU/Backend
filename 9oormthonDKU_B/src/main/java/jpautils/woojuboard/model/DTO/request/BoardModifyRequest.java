package jpautils.woojuboard.model.DTO.request;

import jpautils.woojuboard.model.DTO.response.BoardResponse;
import jpautils.woojuboard.model.DTO.response.ReplyResponse;
import jpautils.woojuboard.model.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor

public class BoardModifyRequest {

    private Long boardNumber;
    private String title;
    private String content;

}
