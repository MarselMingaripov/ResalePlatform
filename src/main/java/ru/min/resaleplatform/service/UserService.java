package ru.min.resaleplatform.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.NewPasswordDto;
import ru.min.resaleplatform.model.dto.UserDto;

import java.util.List;

public interface UserService {

    User createUser(User user);

    UserDto findUser();

    void updatePassword(NewPasswordDto newPasswordDto);

    UserDto updateUser(UserDto userDto);

    User deleteById(int id);

    List<User> findAll();

    void updateImage(MultipartFile image);

    User getCurrentUser();
}
