package org.running.domain.board.repository;


import org.running.domain.board.model.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {

    List<Apply> findByBoard(Board board);
}
