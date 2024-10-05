package jpautils.woojuboard.model.entity;

import jakarta.persistence.*;
import jpautils.woojuboard.model.DTO.request.ReplyPostRequest;
import jpautils.woojuboard.model.DeleteStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql="UPDATE board SET DELETE_STATUS = 'DELETE' WHERE board_number = ?")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNumber;
    // 작성자가 필요 없기 때문에 boardNumber로 대체

    @Column
    private Long poster;
    // 10.4 수정

    @Column
    private String title;
    //제목

    @Column(length = 300)
    private String content;
    // 글 내용

    @Enumerated(EnumType.STRING)
    // 플레그 -> 삭제여부 : Soft Delete
    private DeleteStatus deleteStatus;

    @Column
    private String filePath;
    // 10.4 수정

    @Column
    private LocalDateTime meet;
    // 10.4 수정

    @Column
    private String location;
    // 10.4 수정

    @Column
    private Integer limits;
    // 10.4 수정

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private Users users;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    @SQLRestriction("DELETE_STATUS = 'ACTIVE'")
    private List<Reply> reply = new ArrayList<>();
    public Board addReply(String content,Long replier){
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setBoard(this);
        reply.setDeleteStatus(DeleteStatus.ACTIVE);
        reply.setReplier(replier);
        // 10.4 수정사항 -> replier 추가
        this.getReply().add(reply);
        return this;
    }

    // 10 . 5 수정
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Apply_posts> apply_posts = new ArrayList<>();

    public Board addApply_posts(Apply apply){
        if (apply == null) {
            // 지원자가 없는 경우에 대한 처리 (예: 예외 발생)
            System.out.println("지원자가 없습니다.");
            return this; // 또는 null 반환
        }
        Apply_posts apply_posts = new Apply_posts();
        apply_posts.setBoard(this);
        apply_posts.setApply(apply);  // 리스트에 추가
        this.getApply_posts().add(apply_posts);
        return this;
    }

    // 10 . 6 수정
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    public Board addLikes(Long boardNumber){

        Likes likes = new Likes();
        likes.setBoardNumber(boardNumber);
        likes.setBoard(this);
        this.getLikes().add(likes);
        return this;
    }
}
