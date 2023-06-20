package ru.min.resaleplatform.service;

import ru.min.resaleplatform.model.Comment;
import ru.min.resaleplatform.model.dto.CommentDto;
import ru.min.resaleplatform.model.dto.ResponseWrapperComment;
import ru.min.resaleplatform.model.dto.TextDto;

public interface CommentService {
    ResponseWrapperComment getAllComments(int id);

    CommentDto createComment(int id, TextDto text);

    void deleteComment(int adId, int commentId);

    Comment findById(int id);

    CommentDto updateComment(int adId, int commentId, CommentDto commentDto);
}
