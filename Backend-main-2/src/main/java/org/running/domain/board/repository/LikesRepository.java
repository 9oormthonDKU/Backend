package org.running.domain.board.repository;

import org.running.domain.board.model.entity.Likes;
import org.running.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    List<Likes> findByUser(User user);

}
