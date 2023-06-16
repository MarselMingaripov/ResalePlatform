package ru.min.resaleplatform.service;

import org.junit.jupiter.api.Test;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.security.dto.Role;
import ru.min.resaleplatform.service.impl.ValidationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {
    private ValidationService out = new ValidationServiceImpl();
    private static String EMAIL = "user@gmail.com";
    private static String PASSWORD = "password";
    private static String FIRST_NAME = "Ivan";
    private static String LAST_NAME = "Popov";
    private static String PHONE_NUMBER = "+79053930303";
    private static String IMAGE = "Avatar";

    private static User USER1 = new User(
            "user@gmail.com",
            "1234567",
            "Sergey",
            "Sergeev",
            "+79053093099"
    );
    @Test
    void returnTrueWhenUserIsCorrect() {
        assertTrue(out.validate(new User(
                EMAIL,
                PASSWORD,
                FIRST_NAME,
                LAST_NAME,
                PHONE_NUMBER,
                IMAGE
        )));
    }
    @Test
    void returnTrueWhenEmailIsCorrect() {
        assertTrue(out.validateEmail(EMAIL));
    }
    @Test
    void returnTrueWhenStringIsCorrect() {
        assertTrue(out.validateString(FIRST_NAME));
    }
    @Test
    void returnTrueWhenPhoneIsCorrect() {
        assertTrue(out.validatePhoneNumber(PHONE_NUMBER));
    }
}

