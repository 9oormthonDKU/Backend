package org.running.domian.board.controller;

import org.running.domian.board.model.DTO.request.BoardDeleteRequest;
import org.running.domian.board.model.DTO.request.BoardModifyRequest;
import org.running.domian.board.model.DTO.request.BoardPostRequest;
import org.running.domian.board.model.DTO.response.BoardListResponse;
import org.running.domian.board.model.DTO.response.BoardResponse;
import org.running.domian.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시물 등록
    @PostMapping("/board")
    public BoardResponse makeBoard(@RequestBody BoardPostRequest boardPostRequest){
        return boardService.makeBoard(boardPostRequest);
    }

    // 게시물 목록 조회 (페이징)
    @GetMapping("/board")
    public List<BoardListResponse> boardList(@RequestParam("page") int page, @RequestParam("size") int size){
        return boardService.searchList(page, size);
    }

    // 단건 조회 (특정 게시물 조회)
    @GetMapping("/board/{id}")
    public BoardResponse searchBoard(@PathVariable("id") Long boardNumber){
        return boardService.searchBoard(boardNumber);
    }

    // 게시물 삭제
    @DeleteMapping("/board")
    public String deleteBoard(@RequestBody BoardDeleteRequest boardDeleteRequest){
        return boardService.deleteBoard(boardDeleteRequest);
    }

    // 게시물 수정
    @PatchMapping("/board/{id}")
    public BoardResponse modify(@PathVariable Long id, @RequestBody BoardModifyRequest boardModifyRequest){
        return boardService.modify(boardModifyRequest);
    }
}
