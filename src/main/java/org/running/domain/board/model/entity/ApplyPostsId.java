package jpautils.woojuboard.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
// 복합키를 담고 있는 클래스
public class ApplyPostsId implements Serializable {

    private Long apply_id;
    private Long board_id;

    // 기본 생성자 (JPA에서 필요)
    public ApplyPostsId() {}

    // 인자를 받는 생성자
    public ApplyPostsId(Long apply_id, Long board_id) {
        this.apply_id = apply_id;
        this.board_id = board_id;
    }
}
