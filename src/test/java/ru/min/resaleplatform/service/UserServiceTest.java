package ru.min.resaleplatform.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ValidationService validationServiceMock;

    @InjectMocks
    private UserServiceImpl userServiceOut;

    private static String EMAIL = "user@gmail.com";
    private static String PASSWORD = "password";
    private static String FIRST_NAME = "Ivan";
    private static String LAST_NAME = "Popov";
    private static String PHONE_NUMBER = "+79053930303";
    private static String IMAGE = "Avatar";
    private User user;

    @BeforeEach
    public void init() {
        user = new User(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER, IMAGE);
    }

    @Test
    @DisplayName("Проверка корректного создания нового пользователя")
    public void shouldReturnWhenCreateNewUser() {

        Mockito.when(validationServiceMock.validate(any())).thenReturn(true);
        Mockito.when(userRepositoryMock.save(any())).thenReturn(user);
        assertEquals(user, userServiceOut.createUser(user));
        verify(userRepositoryMock, times(1)).save(any());
    }
}