package org.running.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.running.domain.board.model.DTO.response.ApplyResponse;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.model.entity.Board;
import org.running.domain.board.service.ApplyService;
import org.running.domain.user.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;


    @PostMapping("/apply")
    public ApplyResponse create(@RequestBody BoardResponse boardResponse){
        // SecurityContext에서 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // 사용자 정보가 User 객체라고 가정

        return applyService.create(boardResponse,user);
    }

    @GetMapping("/apply")
    public List<String> read(@RequestBody BoardResponse boardResponse){
        return applyService.read(boardResponse);
    }

    @DeleteMapping("/apply")
    public ResponseEntity<String> delete(@RequestBody BoardResponse boardResponse, @AuthenticationPrincipal User userDetails) {
        Long currentUserId = userDetails.getId(); // 현재 사용자 ID를 가져옵니다.
        // 게시글 번호를 boardResponse에서 추출합니다.
        Long boardNumber = boardResponse.getBoardNumber();

        // 서비스 호출
        String resultMessage = applyService.delete(boardNumber,userDetails);
        return ResponseEntity.ok(resultMessage); // 성공 메시지 반환
    }

    @DeleteMapping("/apply/reject/{applyId}")
    public String rejectApply(@RequestParam Long boardNumber, @PathVariable Long applyId) {
        return applyService.rejectApply(applyId);
    }
}
