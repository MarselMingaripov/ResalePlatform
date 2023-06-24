package ru.min.resaleplatform.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.security.dto.RegisterReq;
import ru.min.resaleplatform.security.dto.Role;
import ru.min.resaleplatform.security.service.AuthService;
import ru.min.resaleplatform.security.service.UserCreatorService;

import javax.validation.ValidationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;
    private final UserCreatorService userCreatorService;

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public boolean login(String userName, String password) {

        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(userName);

        if (!encoder.matches(password, user.getPassword())) {
            logger.warn("the password is incorrect");
            throw new BadCredentialsException("Неверно указан пароль!");
        }
        return true;
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (userRepository.existsByEmail(registerReq.getUsername())) {
            logger.warn("user already exists");
            throw new ValidationException(String.format("Пользователь \"%s\" уже зарегистрирован!", registerReq.getUsername()));
        }

        userCreatorService.createUser(registerReq);
        return true;
    }
}
