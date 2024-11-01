package org.running.domain.board.controller;

import org.running.domain.board.model.DTO.request.BoardDeleteRequest;
import org.running.domain.board.model.DTO.request.BoardModifyRequest;
import org.running.domain.board.model.DTO.request.BoardPostRequest;
import org.running.domain.board.model.DTO.response.BoardListResponse;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/board")
    public List<BoardListResponse> boardList(@RequestParam("page") int page, @RequestParam("size") int size){
        return boardService.searchList(page, size);
    }

    // Read - 단건
    @GetMapping("/board/{id}")
    public BoardResponse searchBoard(@PathVariable("id") Long boardNumber){
        return boardService.searchBoard(boardNumber);
    }

    // Delete
    @DeleteMapping("/board")
    public String deleteBoard(@RequestBody BoardDeleteRequest boardDeleteRequest){
        return boardService.deleteBoard(boardDeleteRequest);
    }

    // Update
    @PatchMapping("/board/{id}")
    public BoardResponse modify(@PathVariable Long id, @RequestBody BoardModifyRequest boardModifyRequest){
        return boardService.modify(boardModifyRequest);
    }
}
