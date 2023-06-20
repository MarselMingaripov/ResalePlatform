package ru.min.resaleplatform.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.security.dto.RegisterReq;
import ru.min.resaleplatform.security.dto.Role;
import ru.min.resaleplatform.security.service.AuthService;
import ru.min.resaleplatform.service.UserService;
import ru.min.resaleplatform.service.impl.UserServiceImpl;

import javax.validation.ValidationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetailsService;

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

        userDetailsService.createUser(registerReq);
        return true;
    }
}
