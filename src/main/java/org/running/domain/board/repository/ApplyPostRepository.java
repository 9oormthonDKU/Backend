package org.running.domain.board.repository;

import org.running.domain.board.model.entity.ApplyPostsId;
import org.running.domain.board.model.entity.Apply_posts;
import org.running.domain.board.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository

public interface ApplyPostRepository extends JpaRepository<Apply_posts, ApplyPostsId> {
    List<Apply_posts> findByBoard(Board board);

}
