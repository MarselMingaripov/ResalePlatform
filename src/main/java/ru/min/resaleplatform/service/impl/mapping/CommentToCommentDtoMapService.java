package ru.min.resaleplatform.service.impl.mapping;

import org.modelmapper.PropertyMap;
import ru.min.resaleplatform.model.Comment;
import ru.min.resaleplatform.model.dto.CommentDto;

public class CommentToCommentDtoMapService extends PropertyMap<Comment, CommentDto> {
    @Override
    protected void configure() {
        map().setAuthor(source.getCommentAuthor().getId());
        map().setAuthorImage(source.getCommentAuthor().getImage());
        map().setAuthorFirstName(source.getCommentAuthor().getFirstName());
        map().setCreatedAt(source.getCreatedAt());
        map().setPk(source.getId());
        map().setText(source.getText());
    }
}
