package ru.min.resaleplatform.security.service.impl;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.Comment;
import ru.min.resaleplatform.security.dto.Role;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static MyUserDetails getUserDetailsFromContext() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getUserIdFromContext() {
        return getUserDetailsFromContext().getId();
    }

    public static void checkPermissionToAds(Ads ads) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != ads.getAdsAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить/удалить объявление, нужно иметь роль ADMIN или быть владельцем этого объявления");
        }
    }

    public static void checkPermissionToAdsComment(Comment adsComment) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != adsComment.getCommentAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить/удалить комментарий, нужно иметь роль ADMIN или быть владельцем этого комментария");
        }
    }
}
