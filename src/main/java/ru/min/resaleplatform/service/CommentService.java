package ru.min.resaleplatform.service;

import ru.min.resaleplatform.model.Comment;
import ru.min.resaleplatform.model.dto.CommentDto;
import ru.min.resaleplatform.model.dto.ResponseWrapperComment;
import ru.min.resaleplatform.model.dto.TextDto;

public interface CommentService {

    /**
     * получить все комментарии в виде dto для фронта
     * @param id
     * @return
     */
    ResponseWrapperComment getAllComments(int id);

    /**
     * создать комментарий
     * @param id
     * @param text
     * @return
     */
    CommentDto createComment(int id, TextDto text);

    /**
     * удалить комментарий
     * @param adId - ид объявления
     * @param commentId - ид комментарий
     */
    void deleteComment(int adId, int commentId);

    /**
     * поиск комментарий по ид
     * @param id
     * @return
     */
    Comment findById(int id);

    /**
     * обновление комментария по ид
     * @param adId - ид объявления
     * @param commentId - ид комментарий
     * @param commentDto
     * @return
     */
    CommentDto updateComment(int adId, int commentId, CommentDto commentDto);
}
