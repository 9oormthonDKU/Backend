package jpautils.woojuboard.controller;

import jpautils.woojuboard.model.DTO.request.BoardDeleteRequest;
import jpautils.woojuboard.model.DTO.request.BoardModifyRequest;
import jpautils.woojuboard.model.DTO.request.BoardPostRequest;
import jpautils.woojuboard.model.DTO.response.BoardListResponse;
import jpautils.woojuboard.model.DTO.response.BoardResponse;
import jpautils.woojuboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private BoardService boardService;

    // 게시물 등록
    @PostMapping("board")
    public BoardResponse makeBoard(@RequestBody BoardPostRequest boardPostRequest){
        return boardService.makeBoard(boardPostRequest);

    }

    // 조회하기
    // 페이징 조회 : 다건 , 댓글 가져오지 않음 , 게시물 목록
    @GetMapping("board")
    public List<BoardListResponse> boardList(@RequestParam("page") int page, @RequestParam("size") int size){
        return boardService.searchList(page,size);
    }

    // 단건 조회
    @GetMapping("board")
    public BoardResponse searchBoard(@RequestParam("boarNumber") Long boardNumber){
        return boardService.searchBoard(boardNumber);
    }

    // 참고 부분 : DeleteMapping
    @DeleteMapping("board")
    public String deleteBoard(@RequestBody BoardDeleteRequest boardDeleteRequest){
        return boardService.deleteBoard(boardDeleteRequest);
    }
    // 게시물 보기

    // 게시물 수정
    @PatchMapping("/board/{id}")
    public BoardResponse modify(@PathVariable Long id, @RequestParam BoardModifyRequest boardModifyRequest){
        return boardService.modify(boardModifyRequest);
    }
}
