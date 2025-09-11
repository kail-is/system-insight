package com.example.controller;

import com.example.dto.AssignmentRequest;
import com.example.service.PostAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostAssignmentController {

    private final PostAssignmentService postAssignmentService;

    @PostMapping("/{postId}/assignments")
    public ResponseEntity<Void> syncAssignments(
            @PathVariable String postId,
            @RequestBody List<AssignmentRequest> requestList) {
        postAssignmentService.syncAssignments(postId, requestList);
        return ResponseEntity.ok().build();
    }
} 