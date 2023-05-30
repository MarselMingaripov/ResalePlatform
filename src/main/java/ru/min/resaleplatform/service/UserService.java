package ru.min.resaleplatform.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.NewPasswordDto;
import ru.min.resaleplatform.model.dto.UserDto;

import java.util.List;

public interface UserService {

    User createUser(User user);

    UserDto findUser();

    void updatePassword(NewPasswordDto newPasswordDto);

    User deleteById(int id);

    List<User> findAll();

    User updateData(User user);

    User updateImage(String image);
}
