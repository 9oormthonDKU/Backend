package org.running.domain.board.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
import org.running.domain.board.model.entity.Board;
import org.running.domain.user.model.User;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Apply_posts와의 OneToMany 관계
    @OneToMany(mappedBy = "apply", cascade = CascadeType.PERSIST)
    private List<Apply_posts> apply_posts = new ArrayList<>();

    public Apply_posts addApply_Posts(Board board) {
        Apply_posts apply_post = new Apply_posts();

        // apply와 board 필드가 null이 아닌지 확인하고 설정
        apply_post.setApply(this);
        apply_post.setBoard(board);

        // 복합 키 설정이 필요한 경우, `ApplyPostsId`도 설정하는 로직을 추가할 수 있습니다.
        // apply_post.setApplyId(this.getId());
        // apply_post.setBoardId(board.getId());

        // 양방향 관계의 일관성 유지
        this.apply_posts.add(apply_post);
        return apply_post;
    }
    @ManyToOne
    @JoinColumn(name="USERS_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id") // 이 부분은 실제 외래 키 이름에 맞춰 조정
    private Board board;

    public void removeApplyPost(Apply_posts applyPost) {
        this.apply_posts.remove(applyPost);
        applyPost.setApply(null); // 관계 해제
    }
}
