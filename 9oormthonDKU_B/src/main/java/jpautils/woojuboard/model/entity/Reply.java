package jpautils.woojuboard.model.entity;

import jakarta.persistence.*;
import jpautils.woojuboard.model.DeleteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    @Column
    private Long replier;
    // 10.4 수정 사항

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_NUMBER")
    private Board board;

    @ManyToOne
    @JoinColumn(name="USERS_ID")
    private Users users;

}
