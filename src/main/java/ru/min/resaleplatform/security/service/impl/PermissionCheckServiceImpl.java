package ru.min.resaleplatform.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.Comment;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.security.service.PermissionCheckService;
import ru.min.resaleplatform.service.UserService;

@Service
@RequiredArgsConstructor
public class PermissionCheckServiceImpl implements PermissionCheckService {

    private final UserService userService;

    @Override
    public boolean checkPermissionToUpdateAds(int adsId, Ads ads){
        User user = userService.getCurrentUser();
        return user.getRole().getAuthority().equals("ADMIN") || ads.getAdsAuthor().getEmail().equals(user.getEmail());

    }

    @Override
    public boolean checkPermissionToUpdateComment(int id, Comment comment){
        User user = userService.getCurrentUser();
        return user.getRole().getAuthority().equals("ADMIN") ||
                comment.getCommentAuthor().getEmail().equals(user.getEmail());
    }
}
