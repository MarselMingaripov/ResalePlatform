package ru.min.resaleplatform.service.impl;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.service.ValidationService;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    private static final Pattern PATTERN_PHONE_NUMBER = Pattern.compile("(^(\\+7)(\\d{10}))$");

    @Override
    public boolean validateEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    @Override
    public boolean validateString(String str) {
        return str != null
                && !StringUtils.isEmpty(str)
                && !StringUtils.isBlank(str);
    }

    @Override
    public boolean validatePhoneNumber(String phone) {
        return PATTERN_PHONE_NUMBER.matcher(phone).matches();
    }

    @Override
    public boolean validate(User user) {
//        return true;
        return user != null
                && validateEmail(user.getEmail())
                && validateString(user.getPassword())
                && validateString(user.getFirstName())
                && validateString(user.getLastName())
                && validatePhoneNumber(user.getPhone())
                && validateString(user.getImage());
    }
}
