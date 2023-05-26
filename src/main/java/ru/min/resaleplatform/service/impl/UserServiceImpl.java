package ru.min.resaleplatform.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.service.UserService;
import ru.min.resaleplatform.service.ValidationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final UserDetailsManager manager;

    @Override
    public User createUser(User user) {
        if (!validationService.validate(user)) {
            return null;
        } else {
            return userRepository.save(user);
        }
    }

    @Override
    public UserDetails findUser() {
        return manager.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public User deleteById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User updatePassword(int id, String password) {
        return null;
    }

    @Override
    public User updateData(User user) {
        return null;
    }

    @Override
    public User updateImage(String image) {
        return null;
    }
}
