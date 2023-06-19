package ru.min.resaleplatform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.min.resaleplatform.model.dto.CommentDto;
import ru.min.resaleplatform.model.dto.ResponseWrapperComment;
import ru.min.resaleplatform.model.dto.TextDto;
import ru.min.resaleplatform.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getAllComments(@PathVariable int id){
        return ResponseEntity.ok(commentService.getAllComments(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable int id, @RequestBody TextDto text){
        return ResponseEntity.ok(commentService.createComment(id, text));
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int adId, @PathVariable int commentId){
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int adId,
                                                    @PathVariable int commentId,
                                                    @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, commentDto));
    }
}
