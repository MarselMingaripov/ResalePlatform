package ru.min.resaleplatform.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.NewPasswordDto;
import ru.min.resaleplatform.model.dto.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    /**
     * создать пользователя
     * @param user
     * @return
     */
    User createUser(User user);

    /**
     * поиск пользователя
     * @return
     */
    UserDto findUser();

    /**
     * обновить пароль пользователя
     * @param newPasswordDto
     */
    void updatePassword(NewPasswordDto newPasswordDto);

    /**
     * обновить пользователя
     * @param userDto
     * @return
     */
    UserDto updateUser(UserDto userDto);

    /**
     * обновить изображение пользователя
     * @param image
     * @throws IOException
     */
    void updateImage(MultipartFile image) throws IOException;

    /**
     * получить текущего пользователя
     * @return
     */
    User getCurrentUser();

    /**
     * поиск пользователя по ид
     * @param id
     * @return
     */
    User findById(int id);
}
