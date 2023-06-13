package ru.min.resaleplatform.service;

import ru.min.resaleplatform.model.User;

public interface ValidationService {

    boolean validate(User user);

    boolean validateEmail(String email);
    boolean validateString(String str);
    boolean validatePhoneNumber(String phone);
}
