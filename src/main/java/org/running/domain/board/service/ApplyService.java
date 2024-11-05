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
import org.running.domain.user.repository.UserRepository;
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

    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public ApplyResponse create(BoardResponse boardResponse, User user){

        Optional<Board> boardOptional = boardRepository.findById(boardResponse.getBoardNumber());
        Board board = boardOptional.orElseThrow(() -> new RuntimeException());

        User persistentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // Apply 엔티티 생성 및 저장
        Apply apply = new Apply();
        apply.setUser(user);  // 현재 사용자 설정
        apply = applyRepository.save(apply);  // Apply 저장 (여기서 apply는 영속화된 상태가 됨)

        // Apply_posts 생성
        Apply_posts applyPost = new Apply_posts();
        applyPost.setApply(apply);  // Apply와 연결
        applyPost.setBoard(board);   // Board와 연결

        // Apply_posts 저장
        applyPostRepository.save(applyPost);  // Apply_posts 저장

        return new ApplyResponse(apply.getId(), user.getId(), board.getBoardNumber()); // 적절한 BoardResponse 반환 처리

    }

    public List<String> read(BoardResponse boardResponse){

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

        // 지원한 사용자 리스트에서 User의 name만 추출
        List<String> applicantsNames = applyPostsList.stream()
                .map(applyPost -> applyRepository.findById(applyPost.getApply_id())  // Apply를 통해 User 조회
                        .map(Apply::getUser)
                        .map(User::getName) // User 객체에서 이름만 추출
                        .orElse(null)) // 없으면 null 처리
                .filter(Objects::nonNull) // null 값 필터링
                .collect(Collectors.toList());

        return applicantsNames; // 지원한 사용자 이름 리스트 반환
    }

    @Transactional
    public String delete(Long boardNumber, User userDetails) {
        // userDetails를 통해 지원자의 ID를 얻고, boardNumber를 통해 특정 게시글을 식별합니다.
        Apply apply = applyRepository.findByUserIdAndBoardBoardNumber(userDetails.getId(), boardNumber)
                .orElseThrow(() -> new RuntimeException("No apply found for this user and board"));

        // Apply_posts를 가져옵니다.
        List<Apply_posts> applyPosts = apply.getApply_posts();

        // Apply_posts가 있다면 연결된 Board 삭제
        if (!applyPosts.isEmpty()) {
            for (Apply_posts applyPost : applyPosts) {
                // 관련된 Board 삭제
                Board board = applyPost.getBoard();
                if (board != null) {
                    boardRepository.delete(board);
                }
                // Apply_posts 삭제
                applyPostRepository.delete(applyPost);
            }
        }

        // Apply 객체 삭제
        applyRepository.delete(apply);
        return "deleted";
    }


    @Transactional
    public String rejectApply(Long applyId) {
        // Apply 객체를 가져옵니다.
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new RuntimeException("Apply not found"));

        // Apply_posts를 가져옵니다.
        List<Apply_posts> applyPosts = apply.getApply_posts();

        // Apply_posts가 없다면 예외를 던집니다.
        if (applyPosts.isEmpty()) {
            throw new IllegalStateException("No apply posts found for this apply");
        }

        // Apply_posts를 통해 연결된 Board를 삭제합니다.
        for (Apply_posts applyPost : applyPosts) {
            // Apply_posts 삭제
            applyPostRepository.delete(applyPost);
        }

        // Apply 객체 삭제
        applyRepository.delete(apply);
        return "deleted";
    }

}
