package org.running.domain.board.controller;

import org.running.domain.board.model.DTO.request.ReplyDeleteRequest;
import org.running.domain.board.model.DTO.request.ReplyModifyRequest;
import org.running.domain.board.model.DTO.request.ReplyPostRequest;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.model.DTO.response.ReplyResponse;
import org.running.domain.board.service.BoardService;
import org.running.domain.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final BoardService boardService;


    @GetMapping("reply")
    public List<ReplyResponse> showReplies(@PathVariable Long boardNumber){

        BoardResponse boardResponse = boardService.searchBoard(boardNumber);
        return replyService.showReplies(boardResponse);
    }

    @PostMapping("reply")
    public BoardResponse makeReply(@RequestBody ReplyPostRequest replyPostRequest){
        return replyService.makeReply(replyPostRequest);
    }

    @DeleteMapping("reply")
    public String deleteBoard(@RequestBody ReplyDeleteRequest replyDeleteRequest){
        return replyService.deleteReply(replyDeleteRequest);
    }

    @PatchMapping("reply/{replyNumber}")
    public ReplyResponse modify(@PathVariable Long replyNumber, @RequestBody ReplyModifyRequest replyModifyRequest){
        replyModifyRequest.setReplyNumber(replyNumber);
        return replyService.modify(replyModifyRequest);
    }
}
