package org.running.domain.board.service;

import lombok.AllArgsConstructor;
import org.running.domain.board.model.DTO.response.ApplyResponse;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.model.entity.Apply;
import org.running.domain.board.model.entity.Apply_posts;
import org.running.domain.board.model.entity.Board;
import org.running.domain.board.repository.ApplyPostRepository;
import org.running.domain.board.repository.ApplyRepository;
import org.running.domain.board.repository.BoardRepository;
import org.running.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplyService {

    @Autowired
    private final ApplyRepository applyRepository;

    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    private final ApplyPostRepository applyPostRepository;

    @Transactional
    public ApplyResponse create(BoardResponse boardResponse, User user){

        Optional<Board> boardOptional = boardRepository.findById(boardResponse.getBoardNumber());
        Board board = boardOptional.orElseThrow(()-> new RuntimeException());

        // Apply 엔티티 생성 및 저장
        Apply apply = new Apply();
        apply.setUser(user);  // 현재 사용자 설정
        applyRepository.save(apply);  // Apply 저장

        // Apply_posts 생성
        Apply_posts applyPost = new Apply_posts();
        applyPost.setApply(apply);  // Apply와 연결
        applyPost.setBoard(board);   // Board와 연결

        // Apply_posts 저장
        // ApplyPostsRepository가 없으면 apply를 통해 직접 addApply_Posts 메소드를 호출할 수도 있음
        apply.addApply_Posts(board);  // Apply에 Apply_posts 추가

        return new ApplyResponse(apply.getId(), user.getId(), board.getBoardNumber()); // 적절한 BoardResponse 반환 처리

    }
    public List<User> read(BoardResponse boardResponse,User user){

        Long boardNumber = boardResponse.getBoardNumber();

        Board board = boardRepository.findById(boardNumber)
                .orElseThrow(()->new RuntimeException());

        // 해당 Board에 대한 모든 Apply_posts 조회
        List<Apply_posts> applyPostsList = applyPostRepository.findByBoard(board);

        // Apply_posts가 존재하지 않는 경우 빈 리스트 반환
        if (applyPostsList.isEmpty()) {
            return Collections.emptyList();
        }

        if (applyPostsList.isEmpty()) {
            return Collections.emptyList();
        }

        List<User> applicants = applyPostsList.stream()
                .map(applyPost -> applyRepository.findById(applyPost.getApply_id())  // Apply를 통해 User 조회
                        .map(Apply::getUser)
                        .orElse(null)) // 없으면 null 처리
                .filter(Objects::nonNull) // null 값 필터링
                .collect(Collectors.toList());
        return applicants; // 지원한 사용자 리스트 반환
    }

    @Transactional
    public String delete(BoardResponse boardResponse){
        Optional<Board> boardOptional = boardRepository.findById(boardResponse.getBoardNumber());
        Board board = boardOptional
                .orElseThrow(RuntimeException::new);

        List<Apply> applyList = applyRepository.findByBoard(board);
        Apply apply = applyList.stream().findFirst()
                .orElseThrow(RuntimeException::new);

        applyRepository.delete(apply);
        return "deleted";
    }
}
