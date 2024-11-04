package org.running.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.running.domain.board.model.DTO.request.LikesDeleteRequest;
import org.running.domain.board.model.DTO.request.LikesMakeRequest;
import org.running.domain.board.model.DTO.response.LikesResponse;
import org.running.domain.board.model.entity.Likes;
import org.running.domain.board.service.LikesService;
import org.running.domain.user.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // Create
    @PostMapping("/likes")
    public LikesResponse makeLikes(@RequestBody LikesMakeRequest likesMakeRequest){
        return likesService.create(likesMakeRequest);
    }

    // Read
    @GetMapping("/likes")
    public List<LikesResponse> showLikes(@RequestBody UserResponse userResponse){
        return likesService.read(userResponse);
    }

    // Delete
    @DeleteMapping("/likes")
    public String deleteLikes(@RequestBody LikesDeleteRequest likesDeleteRequest){
        return likesService.delete(likesDeleteRequest);

    }
}
