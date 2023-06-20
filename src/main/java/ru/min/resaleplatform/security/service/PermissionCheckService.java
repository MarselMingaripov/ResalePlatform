package ru.min.resaleplatform.security.service;

import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.Comment;

public interface PermissionCheckService {
    boolean checkPermissionToUpdateAds(int id, Ads ads);

    boolean checkPermissionToUpdateComment(int id, Comment comment);
}
