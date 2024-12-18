package org.running.domain.board.controller;

import org.running.domain.board.model.DTO.request.BoardDeleteRequest;
import org.running.domain.board.model.DTO.request.BoardModifyRequest;
import org.running.domain.board.model.DTO.request.BoardPostRequest;
import org.running.domain.board.model.DTO.response.BoardListResponse;
import org.running.domain.board.model.DTO.response.BoardMyResponse;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.model.DTO.response.BoardSearchResponse;
import org.running.domain.board.service.BoardService;
import org.running.domain.user.model.User;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // Create
    @PostMapping("/board")
    public BoardResponse makeBoard(@RequestBody BoardPostRequest boardPostRequest){
        return boardService.makeBoard(boardPostRequest);
    }

    // Read - 리스트
    @GetMapping("/board/list")
    public List<BoardListResponse> boardList(@RequestParam("page") int page, @RequestParam("size") int size){
        return boardService.searchList(page, size);
    }

    // Read - 단건
    @GetMapping("/board/{boardNumber}")
    public BoardResponse searchBoard(@PathVariable("boardNumber") Long boardNumber){
        return boardService.searchBoard(boardNumber);
    }


    // 나의 게시글 조회
    @GetMapping("/board/my")
    public List<BoardMyResponse> getMyBoards(@AuthenticationPrincipal User user) {
        return boardService.findBoardsByUser(user);
    }

    // Read - 검색기능
    @GetMapping("/board/search")
    public Page<BoardSearchResponse> searchBoardList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("keyword") String keyword) {
        return boardService.searchBoardList(page, size, keyword);
    }

    @GetMapping("/board/hot")
    public List<BoardResponse> hotList(@RequestParam("boardNumber") Long boardNumber){
        return boardService.searchHotBoard(boardNumber);
    }

    // Delete
    @DeleteMapping("/board/{boardNumber}")
    public String deleteBoard(@PathVariable("boardNumber") Long boardNumber) {
        return boardService.deleteBoard(boardNumber);
    }


    // Update
    @PatchMapping("/board/{boardNumber}")
    public BoardResponse modify(@PathVariable("boardNumber") Long boardNumber, @RequestBody BoardModifyRequest boardModifyRequest){
        boardModifyRequest.setBoardNumber(boardNumber);
        return boardService.modify(boardModifyRequest);
    }
}
