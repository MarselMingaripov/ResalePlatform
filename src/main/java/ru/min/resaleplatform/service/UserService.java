package ru.min.resaleplatform.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.min.resaleplatform.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    UserDetails findUser();

    User deleteById(int id);

    List<User> findAll();

    User updatePassword(int id, String password);

    User updateData(User user);

    User updateImage(String image);
}
