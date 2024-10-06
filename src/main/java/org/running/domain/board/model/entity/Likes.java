package jpautils.woojuboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
// 10.4 생성 및 수정
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long boardNumber;


    @ManyToOne
    @JoinColumn(name = "BOARD_NUMBER")
    private Board board;


    @ManyToOne
    @JoinColumn(name="USERS_ID")
    private Users users;

}
