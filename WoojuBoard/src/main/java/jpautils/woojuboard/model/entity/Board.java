package jpautils.woojuboard.model.entity;

import jakarta.persistence.*;
import jpautils.woojuboard.model.DTO.request.ReplyPostRequest;
import jpautils.woojuboard.model.DeleteStatus;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

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
    private String title;
    //제목

    @Column(length = 300)
    private String content;
    // 글 내용

    @Enumerated(EnumType.STRING)
    // 플레그 -> 삭제여부 : Soft Delete
    private DeleteStatus deleteStatus;


    /*
    해설강의 필기
    Active 상태인 것만 가져오도록 @SQLRestriction 사용
     */
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    @SQLRestriction("DELETE_STATUS = 'ACTIVE'")
    private List<Reply> reply = new ArrayList<>();

    public Board addReply(String content){
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setBoard(this);
        reply.setDeleteStatus(DeleteStatus.ACTIVE);

        this.getReply().add(reply);
        return this;
    }


}
