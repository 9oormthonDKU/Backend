package org.running.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.running.domain.board.model.DTO.request.LikesDeleteRequest;
import org.running.domain.board.model.DTO.request.LikesMakeRequest;
import org.running.domain.board.model.DTO.response.LikesResponse;
import org.running.domain.board.service.LikesService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // Create
    @PostMapping("/likes")
    public LikesResponse makeLikes(@RequestBody LikesMakeRequest likesMakeRequest) {
        return likesService.create(likesMakeRequest);
    }

    // Read
    @GetMapping("/likes")
    public Long showLikes(@RequestParam Long boardNumber) {
        return likesService.readUserCount(boardNumber);
    }

    // Delete
    @DeleteMapping("/likes/{id}")
    public String deleteLikes(@PathVariable Long id) {
        return likesService.delete(id);
    }
}
