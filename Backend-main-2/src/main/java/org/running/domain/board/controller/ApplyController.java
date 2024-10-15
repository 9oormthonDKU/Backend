package org.running.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.running.domain.board.model.DTO.response.ApplyResponse;
import org.running.domain.board.service.ApplyService;
import org.running.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;


    @PostMapping("/apply")
    public ApplyResponse create(@RequestBody ApplyResponse applyResponse, User user){
        return applyService.create(applyResponse,user);
    }

}
