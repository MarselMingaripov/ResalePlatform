package ru.min.resaleplatform.service;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.UserDto;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.service.impl.ImageServiceImpl;
import ru.min.resaleplatform.service.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ValidationService validationServiceMock;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private SecurityContextHolder securityContextHolder;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private ImageService mockImageService;;
    @Mock
    private User mockUser;

    @InjectMocks
    private UserServiceImpl userServiceOut;
    @InjectMocks
    private ImageServiceImpl imageServiceOut;

    private User user;

    private static String EMAIL = "user@gmail.com";
    private static String PASSWORD = "password";
    private static String FIRST_NAME = "Ivan";
    private static String LAST_NAME = "Popov";
    private static String PHONE_NUMBER = "+79053930303";
    private static String IMAGE = "Avatar";

    @BeforeEach
    public void init() {
        user = new User(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER, IMAGE);

//        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Проверка корректного создания нового пользователя")
    public void shouldReturnWhenCreateNewUser() {

        user = new User(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER, IMAGE);
        Mockito.when(validationServiceMock.validate(any())).thenReturn(true);
        Mockito.when(userRepositoryMock.save(any())).thenReturn(user);
        assertEquals(user, userServiceOut.createUser(user));
        verify(userRepositoryMock, times(1)).save(any());
    }

    @Test
    @DisplayName("Проверка поиска пользователя")
    public void shouldReturnExpectedFindUserDto() {

        UserDto expectedUserDto = new UserDto();
//        expectedUserDto.setId(1);
        expectedUserDto.setEmail("user@gmail.com");
        expectedUserDto.setFirstName("John");
        expectedUserDto.setLastName("Doe");
        expectedUserDto.setPhone("+79059059055");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(modelMapper.map(user, UserDto.class)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userServiceOut.findUser();
        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    @DisplayName("Проверка обновления пользователя")
    void shouldReturnUpdateUser() {
        User user = new User();
        UserDto userDto = new UserDto();

        user.setEmail("user@gmail.com");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPhone("+79059059055");

        userDto.setId(0);
        userDto.setEmail("user@gmail.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setPhone("+79059059055");
        userDto.setImage(null);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("user@gmail.com");
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);


        assertEquals(user, userDto);

    }

    @Test
    void shouldReturnGetCurrentUser() {

        String email = "user@example.com";
        User expectedUser = new User();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        User currentUser = userServiceOut.getCurrentUser();
        assertEquals(expectedUser, currentUser);
    }

    @Test
    void updateImage() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "image.jpg", "image/jpeg", "image data".getBytes());
        Mockito.when(mockUser.toString()).thenReturn("Test User");
        Authentication auth = Mockito.mock(Authentication.class);
//        Mockito.when(auth.getAuthorities()).thenReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}