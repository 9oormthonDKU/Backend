package org.running.domain.board.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(ApplyPostsId.class)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apply_posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apply_id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    // ManyToOne 관계 설정
    // 정류장 역할의 엔티티 이기 때문에 insertable , updateable은 모두 false 처리
    @ManyToOne
    @JoinColumn(name = "apply_id", insertable = false, updatable = false)
    private Apply apply;

    // 정류장 역할의 엔티티 이기 때문에 insertable , updateable은 모두 false 처리
    @ManyToOne
    @JoinColumn(name = "board_id", insertable = false, updatable = false)
    private Board board;
}
