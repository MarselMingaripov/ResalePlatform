package ru.min.resaleplatform.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.min.resaleplatform.exception.AccessException;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.Comment;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.*;
import ru.min.resaleplatform.repository.AdsRepository;
import ru.min.resaleplatform.repository.CommentRepository;
import ru.min.resaleplatform.security.service.PermissionCheckService;
import ru.min.resaleplatform.service.CommentService;
import ru.min.resaleplatform.service.UserService;
import ru.min.resaleplatform.service.impl.mapping.CommentToCommentDtoMapService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final PermissionCheckService permissionCheckService;
    private final ModelMapper mapper;

    @Override
    public ResponseWrapperComment getAllComments(int id) {
        List<CommentDto> commentDtos = new ArrayList<>();
        if (adsRepository.existsById(id)) {
            Ads ads = adsRepository.findById(id).orElseThrow();
            List<Comment> comments = ads.getComments();
            for (Comment comment : comments) {
                commentDtos.add(new CommentDto(
                        comment.getCommentAuthor().getId(),
                        comment.getCommentAuthor().getImage(),
                        comment.getCommentAuthor().getFirstName(),
                        comment.getCreatedAt(),
                        comment.getId(),
                        comment.getText()
                ));
            }
        }
        return new ResponseWrapperComment(commentDtos.size(), commentDtos);
    }

    @Override
    public CommentDto createComment(int id, TextDto text) {
        CommentDto commentDto = new CommentDto();
        if (adsRepository.existsById(id)) {
            User user = userService.getCurrentUser();
            Ads ads = adsRepository.findById(id).orElseThrow();
            Comment comment = new Comment(text.getText(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATTER)), user, ads);
            commentRepository.save(comment);
            ads.getComments().add(comment);
            adsRepository.save(ads);
            commentDto = mapper.map(comment, CommentDto.class);
        }
        return commentDto;
    }

    @Override
    public void deleteComment(int adId, int commentId) {
        Comment commentForRemove = new Comment();
        if (adsRepository.existsById(adId) && commentRepository.existsById(commentId)) {
            Ads ads = adsRepository.findById(adId).orElseThrow();
            for (Comment comment : ads.getComments()) {
                if (comment.getId() == commentId) {
                    if (permissionCheckService.checkPermissionToUpdateComment(commentId, comment)) {
                        commentForRemove = comment;
                    } else {
                        throw new AccessException("Access denied");
                    }
                }
            }
            if (commentForRemove != null) {
                ads.getComments().remove(commentForRemove);
                commentRepository.deleteById(commentId);
            }
        } else {
            throw new NotFoundException("Not found");
        }
    }

    @Override
    public Comment findById(int id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    public CommentDto updateComment(int adId, int commentId, CommentDto commentDto) {
        CommentDto commentDtoForFront = new CommentDto();
        if (adsRepository.existsById(adId) && commentRepository.existsById(commentId)) {
            Ads ads = adsRepository.findById(adId).orElseThrow();
            for (Comment comment : ads.getComments()) {
                if (comment.getId() == commentId) {
                    if (permissionCheckService.checkPermissionToUpdateComment(commentId, comment)) {
                        comment.setText(commentDto.getText());
                        comment.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATTER)));
                        commentRepository.save(comment);
                        commentDtoForFront = mapper.map(comment, CommentDto.class);
                    } else {
                        throw new AccessException("Access denied");
                    }
                }
            }
        } else {
            throw new NotFoundException("Not found");
        }
        return commentDtoForFront;
    }
}
