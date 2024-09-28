package jpautils.woojuboard.controller;

import jpautils.woojuboard.model.DTO.request.BoardDeleteRequest;
import jpautils.woojuboard.model.DTO.request.ReplyDeleteRequest;
import jpautils.woojuboard.model.DTO.request.ReplyModifyRequest;
import jpautils.woojuboard.model.DTO.request.ReplyPostRequest;
import jpautils.woojuboard.model.DTO.response.BoardResponse;
import jpautils.woojuboard.model.DTO.response.ReplyResponse;
import jpautils.woojuboard.service.BoardService;
import jpautils.woojuboard.service.ReplyService;
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
