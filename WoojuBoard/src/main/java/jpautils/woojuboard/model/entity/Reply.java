package jpautils.woojuboard.model.entity;

import jakarta.persistence.*;
import jpautils.woojuboard.model.DeleteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql="UPDATE board SET DELETE_STATUS = 'DELETE' WHERE reply_number = ?")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNumber;
    // 작성자가 필요 없기 때문에 replyNo로 대체

    @Column(length = 300)
    private String content;

    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteStatus;

    /*
    해설강의 필기
    N + 1 문제 : 댓글만 조회를 하고 싶은데 댓글 수 + 보드 1개 만큼 쿼리가 날아가는 문제
    지연로딩 적용 : DB가 아닌 프록시에서 데이터를 가져온다. 엔티티 (프록시)객체에서 데이터를 가져온다.
    -> 이런식의 사용은 n+1에서 +1 만큼 보드 조회를 해결
    n개의 쿼리가 발생 -> JOIN FETCH 라는 것을 이용
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_NUMBER")
    private Board board;



}
