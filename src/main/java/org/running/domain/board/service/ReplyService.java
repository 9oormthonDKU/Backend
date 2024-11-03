package org.running.domain.board.service;

import org.running.domain.board.model.DTO.request.ReplyDeleteRequest;
import org.running.domain.board.model.DTO.request.ReplyModifyRequest;
import org.running.domain.board.model.DTO.request.ReplyPostRequest;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.model.DTO.response.ReplyResponse;
import org.running.domain.user.dto.UserResponse;
import org.running.domain.board.model.DeleteStatus;
import org.running.domain.user.model.User;
import org.running.domain.board.model.entity.Board;
import org.running.domain.board.model.entity.Reply;
import org.running.domain.board.repository.BoardRepository;
import org.running.domain.board.repository.ReplyRepository;
import org.running.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    @Autowired
    private final ReplyRepository replyRepository;
    @Autowired
    private final BoardRepository boardRepository;
    @Autowired
    private final UserRepository userRepository;


    // 댓글 조회기능
    public List<ReplyResponse> showReplies(BoardResponse boardResponse) {
        Long boardNumber = boardResponse.getBoardNumber();

        Board board = boardRepository.findById(boardNumber)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        // 댓글 부분부터 작업하기

        return replyRepository.findByBoard(board)
                .stream()
                .map(ReplyResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardResponse makeReply(ReplyPostRequest replyPostRequest){

        Optional<Board> getBoard = boardRepository.findBoardWithContentsByBoardNumber(replyPostRequest.getBoardNumber());
        Board board = getBoard.orElseThrow(()-> new RuntimeException());

        User user = userRepository.findById(replyPostRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        board.addReply(replyPostRequest.getContent(),replyPostRequest.getUser());
        boardRepository.save(board);

        return BoardResponse.from(board);
    }

    @Transactional
    public String deleteReply(ReplyDeleteRequest replyDeleteRequest){

        Optional<Reply> replyOptional = replyRepository.findById(replyDeleteRequest.getReplyNumber());
        Reply reply = replyOptional.orElseThrow(()->new RuntimeException());

        reply.setDeleteStatus(DeleteStatus.DELETE);
        replyRepository.delete(reply);
        return "Deleted";
    }

    // 댓글 수정 기능
    @Transactional
    public ReplyResponse modify(ReplyModifyRequest replyModifyRequest){

        Optional<Reply> replyOptional = replyRepository.findById(replyModifyRequest.getReplyNumber());
        Reply reply = replyOptional.orElseThrow(()-> new RuntimeException());

        if (replyModifyRequest.getContent() != null) {
            reply.setContent(replyModifyRequest.getContent());
        }

        return ReplyResponse.from(replyRepository.save(reply));
    }
}
