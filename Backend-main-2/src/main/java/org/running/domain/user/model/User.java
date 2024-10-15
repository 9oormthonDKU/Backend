package org.running.domain.user.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.running.domain.board.model.entity.Apply;
import org.running.domain.board.model.entity.Likes;
import org.running.domain.board.model.entity.Reply;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    // gender 컬럼 추가
    // 1: 남자, 2: 여자
    @Column(name = "gender", nullable = false)
    private Integer gender;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "distance", nullable = false)
    private Integer distance;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "image_url")
    private String imageUrl; // 프로필 이미지

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Apply> apply = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reply> reply = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, Integer gender, LocalDate birth,
                String location, Integer distance, String auth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.location = location;
        this.distance = distance;
        apply = new ArrayList<>();
    }

    private User addLikes(Long id) {
        Likes likes = new Likes();
        likes.setUser(this);
        this.getLikes().add(likes);
        return this;
    }

    private User addApply(Long id) {
        Apply apply = new Apply();
        apply.setUserId(id);
        apply.setUser(this);
        this.getApply().add(apply);
        return this;
    }

    private User addReply(Long id) {
        Reply reply = new Reply();
        reply.setReplyNumber(id);
        reply.setUser(this);
        this.getReply().add(reply);
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
