package org.running.domain.board.repository;

import org.running.domain.board.model.DeleteStatus;
import org.running.domain.board.model.entity.Apply;
import org.running.domain.board.model.entity.Board;
import org.springframework.data.domain.Page; // 수정된 부분
import org.springframework.data.domain.Pageable; // 수정된 부분
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 수정된 부분: Spring Data JPA의 Page와 Pageable을 사용
    Page<Board> findAllByDeleteStatus(DeleteStatus deleteStatus, Pageable pageable);

    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.content c WHERE b.boardNumber = :boardNumber")
    Optional<Board> findBoardWithContentsByBoardNumber(@Param("boardNumber") Long boardNumber);



}
