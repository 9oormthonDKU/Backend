package org.running.domain.board.service;

import org.running.domain.board.model.DTO.request.BoardDeleteRequest;
import org.running.domain.board.model.DTO.request.BoardModifyRequest;
import org.running.domain.board.model.DTO.request.BoardPostRequest;
import org.running.domain.board.model.DTO.response.BoardListResponse;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.model.DeleteStatus;
import org.running.domain.board.model.entity.Board;
import org.running.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;
    @Transactional
    public BoardResponse makeBoard(BoardPostRequest boardPostrequest){

        Board board = new Board();
        board.setTitle(boardPostrequest.getTitle());
        board.setContent(boardPostrequest.getContent());
        board.setDeleteStatus(DeleteStatus.ACTIVE);
        board.setWhen_meet(boardPostrequest.getWhen_meet());
        board.setLimits(boardPostrequest.getLimits());
        board.setDistance(boardPostrequest.getDistance());
        board.setPace(boardPostrequest.getPace());

        return BoardResponse.from(boardRepository.save(board));
    }

    // 참고 부분 : 리스트 페이징
    public List<BoardListResponse> searchList(int page,int size){
        return boardRepository.findAllByDeleteStatus(
                DeleteStatus.ACTIVE,
                PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"boardNumber"))
        ).map(BoardListResponse::from).toList();
    }

    // 참고 부분 : 예외 처리 , 매핑
    public BoardResponse searchBoard(Long boardNumber){
        return boardRepository.findBoardWithContentsByBoardNumber(boardNumber)
                .map(BoardResponse::from)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 게시글"));

    }

    public List<BoardResponse> searchHotBoard(Long boardNumber){
        Optional<Board> boardOptional= boardRepository.findById(boardNumber);
        return boardOptional.stream() // 모든 게시글에 대한 스트림 생성
                .filter(board -> board.getLikes().size() >= 10) // 좋아요가 10개 이상인 게시글만 필터링
                .map(BoardResponse::from) // Board 객체를 BoardResponse로 변환
                .collect(Collectors.toList()); // 결과를 리스트로 수집
    }


    @Transactional
    public String deleteBoard(BoardDeleteRequest boardDeleteRequest){
        Optional<Board> boardOptional = boardRepository.findById(boardDeleteRequest.getBoardNumber());
        Board board = boardOptional.orElseThrow(()->new RuntimeException());

        board.setDeleteStatus(DeleteStatus.DELETE);
        boardRepository.delete(board);
        return "Deleted";
    }

    @Transactional
    public BoardResponse modify(BoardModifyRequest boardModifyRequest){
        Optional<Board> boardOptional = boardRepository.findById(boardModifyRequest.getBoardNumber());

        Board board = boardOptional.orElseThrow(()->new RuntimeException());

        if (boardModifyRequest.getTitle() != null) {
            board.setTitle(boardModifyRequest.getTitle());
        }
        if (boardModifyRequest.getContent() != null) {
            board.setContent(boardModifyRequest.getContent());
        }

        board.setLocation(boardModifyRequest.getLocation());
        board.setDistance(boardModifyRequest.getDistance());
        board.setLimits(boardModifyRequest.getLimits());
        board.setPace(boardModifyRequest.getPace());

        return BoardResponse.from(boardRepository.save(board));

    }
}
