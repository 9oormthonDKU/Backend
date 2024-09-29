package org.running.domian.board.repository;

import org.running.domian.board.model.entity.Board;
import org.running.domian.board.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Long> {
    List<Reply> findByBoard(Board board); // Board 객체로 댓글 조회
}