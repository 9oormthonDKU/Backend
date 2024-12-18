package org.running.domain.board.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.running.domain.board.model.DeleteStatus;
import org.running.domain.user.model.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE board SET DELETE_STATUS = 'DELETE' WHERE board_number = ?")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNumber;
    // 작성자가 필요 없기 때문에 boardNumber로 대체

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
    private String location;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime when_meet;

    @Column
    private Integer limits;

    @Column
    private Long distance;

    @Column
    private Long pace;

    @Column
    private Integer statement;

    /*
    해설강의 필기
    Active 상태인 것만 가져오도록 @SQLRestriction 사용
     */
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @SQLRestriction("DELETE_STATUS = 'ACTIVE'")
    private List<Reply> reply = new ArrayList<>();

    public Board addReply(String content, User user) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setBoard(this);
        reply.setUser(user);
        reply.setDeleteStatus(DeleteStatus.ACTIVE);

        this.getReply().add(reply);
        return this;
    }

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    public Board addLikes() {
        Likes likes = new Likes();
        likes.setBoard(this);
        this.getLikes().add(likes);
        return this;
    }

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Apply_posts> applyPosts = new ArrayList<>();

    public Board addApplyPost(Apply_posts applyPost) {
        applyPost.setBoard(this); // Apply_posts와 Board 연결
        this.getApplyPosts().add(applyPost); // 리스트에 추가
        return this; // 메소드 체이닝을 위해 this 반환
    }

    // 지원자 없애기
    public void removeApply(Apply apply) {
        this.applyPosts.remove(apply);
        apply.setBoard(null);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NUMBER")
    private User user;
}
