package jpautils.woojuboard.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    // Apply_posts와의 OneToMany 관계
    @OneToMany(mappedBy = "apply")
    private List<Apply_posts> apply_posts = new ArrayList<>();

    public Apply_posts addApply_Posts(Board board) {
        Apply_posts apply_post = new Apply_posts();
        apply_post.setApply(this);  // 현재 Apply 객체를 설정
        apply_post.setBoard(board);  // 연결할 Board 객체 설정 (이 경우 Board 객체를 파라미터로 전달)
        apply_posts.add(apply_post);  // 리스트에 추가
        return apply_post;  // 생성한 Apply_posts 객체 반환
    }

    @ManyToOne
    @JoinColumn(name="USERS_ID")
    private Users users;

}
