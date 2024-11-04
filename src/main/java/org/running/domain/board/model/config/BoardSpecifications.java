package org.running.domain.board.model.config;

import org.running.domain.board.model.entity.Board;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecifications {
    public static Specification<Board> search(String keyword) {
        return (root, query, criteriaBuilder) -> {
            // 검색할 조건 정의
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), "%" + keyword + "%")
                    // 검색기준 : 제목
            );
        };
    }

}
