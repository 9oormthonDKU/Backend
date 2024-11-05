package org.running.domain.board.repository;


import org.running.domain.board.model.entity.Apply;
import org.running.domain.board.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    List<Apply> findByBoard(Board board);
    Optional<Apply> findByBoardBoardNumberAndUserId(Long boardNumber, Long userId);
    Optional<Apply> findByUserIdAndBoardBoardNumber(Long userId, Long boardNumber);

}
