package jpautils.woojuboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import java.util.*;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String passwd;

    @Column
    private String passCheck;

    @Column
    private LocalDateTime birthDay;

    @Column
    private Integer distance;

    @Column
    private String location;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    private Users addLikes(Long id){
        Likes likes = new Likes();
        likes.setUsers(this);
        this.getLikes().add(likes);
        return this;
    }

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Apply> apply = new ArrayList<>();

    private Users addApply(Long id){
        Apply apply = new Apply();
        apply.setUserId(id);
        apply.setUsers(this);
        this.setApply().add(apply);
        return this;
    }

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Reply> reply = new ArrayList<>();

    private Users addReply(Long id){
        Reply reply = new Reply();
        reply.setReplier(id);
        reply.setUsers(this);
        this.getReply().add(reply);
        return this;
    }

















}
