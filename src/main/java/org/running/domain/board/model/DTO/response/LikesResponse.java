package org.running.domain.board.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.running.domain.board.model.entity.Likes;

@Data
@AllArgsConstructor
public class LikesResponse {

    private Long id;
    public static LikesResponse from(Likes likes){
        return new LikesResponse(
                likes.getId());
    }
}
