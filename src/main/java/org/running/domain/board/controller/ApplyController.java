package org.running.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.running.domain.board.model.DTO.response.ApplyResponse;
import org.running.domain.board.model.DTO.response.BoardResponse;
import org.running.domain.board.model.entity.Board;
import org.running.domain.board.service.ApplyService;
import org.running.domain.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;


    @PostMapping("/apply")
    public ApplyResponse create(@RequestBody BoardResponse boardResponse, User user){
        return applyService.create(boardResponse,user);
    }

    @GetMapping("/apply")
    public List<User> read(@RequestBody BoardResponse boardResponse){
        return applyService.read(boardResponse);
    }

    @DeleteMapping("/apply")
    public String delete(@RequestBody BoardResponse boardResponse){
        return applyService.delete(boardResponse);
    }

    @DeleteMapping("/apply/reject")
    public String rejectApply(@RequestBody BoardResponse boardResponse, @PathVariable Long applyId) {
        return applyService.rejectApply(boardResponse, applyId);
    }
}
