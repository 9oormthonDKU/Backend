package org.running.domain.board.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.running.domain.board.model.DTO.request.ReplyDeleteRequest;
import org.running.domain.board.model.DTO.request.ReplyModifyRequest;
import org.running.domain.board.model.DTO.request.ReplyPostRequest;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.model.DTO.response.ReplyResponse;
import org.running.domain.board.service.BoardService;
import org.running.domain.board.service.ReplyService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final BoardService boardService;


    // Read
    @GetMapping("/reply/{boardNumber}")
    public List<ReplyResponse> showReplies(@PathVariable Long boardNumber) {

        BoardResponse boardResponse = boardService.searchBoard(boardNumber);
        return replyService.showReplies(boardResponse);
    }

    // Create
    @PostMapping("/reply")
    public BoardResponse makeReply(@RequestBody ReplyPostRequest replyPostRequest) {
        return replyService.makeReply(replyPostRequest);
    }

    // Delete
    @DeleteMapping("/reply/{replyNumber}")
    public String deleteReply(@PathVariable Long replyNumber) {
        return replyService.deleteReply(replyNumber);
    }

    // Update
    @PatchMapping("/reply/{replyNumber}")
    public ReplyResponse modify(@PathVariable Long replyNumber, @RequestBody ReplyModifyRequest replyModifyRequest) {
        replyModifyRequest.setReplyNumber(replyNumber);
        return replyService.modify(replyModifyRequest);
    }
}
