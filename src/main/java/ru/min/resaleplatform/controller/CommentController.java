package ru.min.resaleplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Получение комментария",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getAllComments(@PathVariable int id){
        return ResponseEntity.ok(commentService.getAllComments(id));
    }

    @Operation(summary = "Создание комментария",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Объявление не найдено по ид"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable int id, @RequestBody TextDto text){
        return ResponseEntity.ok(commentService.createComment(id, text));
    }

    @Operation(summary = "Удаление комментария",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
                    @ApiResponse(responseCode = "404", description = "Объявление или комментарий не найдены по ид"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int adId, @PathVariable int commentId){
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление комментария",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
                    @ApiResponse(responseCode = "404", description = "Объявление или комментарий не найдены по ид"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int adId,
                                                    @PathVariable int commentId,
                                                    @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, commentDto));
    }
}
