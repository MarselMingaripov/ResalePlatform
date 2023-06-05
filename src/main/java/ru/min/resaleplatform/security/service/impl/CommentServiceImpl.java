package ru.min.resaleplatform.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.Comment;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.*;
import ru.min.resaleplatform.repository.AdsRepository;
import ru.min.resaleplatform.repository.CommentRepository;
import ru.min.resaleplatform.service.CommentService;
import ru.min.resaleplatform.service.UserService;

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

    @Override
    public ResponseWrapperComment getAllComments(int id){
        List<CommentDto> commentDtos = new ArrayList<>();
        if (adsRepository.existsById(id)){
            Ads ads = adsRepository.findById(id).get();
            List<Comment> comments = ads.getComments();
            for (Comment comment : comments) {
                commentDtos.add(new CommentDto(
                        ads.getAdsAuthor().getId(),
                        ads.getAdsAuthor().getImage(),
                        ads.getAdsAuthor().getFirstName(),
                        comment.getCreatedAt(),
                        comment.getId(),
                        comment.getText()
                ));
            }
        }
        return new ResponseWrapperComment(commentDtos.size(), commentDtos);
    }

    @Override
    public CommentDto createComment(int id, TextDto text){
        CommentDto commentDto = new CommentDto();
        if (adsRepository.existsById(id)){
            User user = userService.getCurrentUser();
            Ads ads = adsRepository.findById(id).get();
            Comment comment = new Comment(text.getText(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATTER)), user, ads);
            commentRepository.save(comment);
            ads.getComments().add(comment);
            adsRepository.save(ads);
            commentDto.setAuthor(user.getId());
            commentDto.setAuthorImage(user.getImage());
            commentDto.setAuthorFirstName(user.getFirstName());
            commentDto.setCreatedAt(comment.getCreatedAt());
            commentDto.setPk(comment.getId());
            commentDto.setText(text.getText());
        }
        return commentDto;
    }

    @Override
    public void deleteComment(int adId, int commentId){
        if (adsRepository.existsById(adId) && commentRepository.existsById(commentId)){
            Ads ads = adsRepository.findById(adId).get();
            for (Comment comment : ads.getComments()) {
                if (comment.getId() == commentId){
                    ads.getComments().remove(comment);
                    commentRepository.deleteById(commentId);
                }
            }
        }
    }

    @Override
    public CommentDto updateComment(int adId, int commentId, CommentDto commentDto){
        CommentDto commentDtoForFront = new CommentDto();
        if (adsRepository.existsById(adId) && commentRepository.existsById(commentId)){
            Ads ads = adsRepository.findById(adId).get();
            for (Comment comment : ads.getComments()) {
                if (comment.getId() == commentId){
                    comment.setText(commentDto.getText());
                    comment.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATTER)));
                    commentRepository.save(comment);
                    commentDtoForFront.setAuthor(comment.getCommentAuthor().getId());
                    commentDtoForFront.setAuthorImage(comment.getCommentAuthor().getImage());
                    commentDtoForFront.setAuthorFirstName(comment.getCommentAuthor().getFirstName());
                    commentDtoForFront.setCreatedAt(comment.getCreatedAt());
                    commentDtoForFront.setPk(commentId);
                    comment.setText(comment.getText());
                }
            }
        }
        return commentDtoForFront;
    }
}
