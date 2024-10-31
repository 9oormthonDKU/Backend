package org.running.domain.board.service;

import lombok.AllArgsConstructor;
import org.running.domain.board.model.DTO.request.LikesDeleteRequest;
import org.running.domain.board.model.DTO.request.LikesMakeRequest;
import org.running.domain.board.model.DTO.response.LikesResponse;
import org.running.domain.board.model.entity.Board;
import org.running.domain.board.model.entity.Likes;
import org.running.domain.board.repository.BoardRepository;
import org.running.domain.board.repository.LikesRepository;
import org.running.domain.user.dto.UserResponse;
import org.running.domain.user.model.User;
import org.running.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikesService {

    @Autowired
    private final LikesRepository likesRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BoardRepository boardRepository;

    // CRUD : R -> 좋아요 누른 사람들을 불러오는 메소드
    public List<LikesResponse> read(UserResponse userResponse){
        Long userId = userResponse.getId();  // 수정된 부분

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException());

        return likesRepository.findByUser(user)
                .stream()
                .map(LikesResponse::from)
                .collect(Collectors.toList());
    }

    // CRUD : R -> 좋아요 누른 사람들의 수를 불러오는 메소드
    public long readUserCount(UserResponse userResponse) {
        Long userId = userResponse.getId();  // 수정된 부분

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Likes> likes = likesRepository.findByUser(user);
        return likes.size();  // 좋아요를 누른 사람의 수를 반환
    }

    // CRUD : C -> 좋아요 누르면 리스트에 등록

    @Transactional
    public LikesResponse create(LikesMakeRequest likesMakeRequest){
        Long boardId = likesMakeRequest.getBoard_Id();  // 수정된 부분
        Long userId = likesMakeRequest.getUser_Id();    // 수정된 부분

        // 사용자와 게시글 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        // 좋아요 객체 생성
        Likes like = new Likes();
        like.setUser(user);
        like.setBoard(board);

        // 게시글의 좋아요 리스트에 추가
        board.getLikes().add(like);  // 'likes' 리스트가 Board에 매핑되어 있어야 함



        // 좋아요 저장
        likesRepository.save(like);

        // 저장된 좋아요 정보를 LikesResponse로 변환하여 반환
        return LikesResponse.from(like);

    }

    // CRUD : D -> 좋아요 취소 기능
    @Transactional
    public String delete(LikesDeleteRequest likesDeleteRequest){

        Optional<Likes> likesOptional = likesRepository.findById(likesDeleteRequest.getId());
        Likes likes = likesOptional.orElseThrow(()->new RuntimeException());

        likesRepository.delete(likes);
        return "Deleted";
    }



}
