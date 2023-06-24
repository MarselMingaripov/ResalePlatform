package ru.min.resaleplatform.service;

import ru.min.resaleplatform.model.User;

public interface ValidationService {

    /**
     * валидация пользователя
     * @param user
     * @return
     */
    boolean validate(User user);
}
