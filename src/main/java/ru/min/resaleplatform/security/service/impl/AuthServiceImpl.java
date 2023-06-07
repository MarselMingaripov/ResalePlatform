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
    //private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public boolean login(String userName, String password) {

        /*if (!userRepository.existsByEmail(userName)){
            return false;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password)

        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()));
        return encoder.matches(password, userRepository.findByEmail(userName).get().getPassword());*/

        UserDetails user = userDetailsService.loadUserByUsername(userName);

        if (!encoder.matches(password, user.getPassword())) {
            logger.warn("the password is incorrect");
            throw new BadCredentialsException("Неверно указан пароль!");
        }
        logger.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()));
        logger.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
        return true;
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        /*userRepository.save(new ru.min.resaleplatform.model.User(registerReq.getUsername(), encoder.encode(registerReq.getPassword()), registerReq.getFirstName(),
                registerReq.getLastName(), registerReq.getPhone(), role));
        return true;*/
        if (userRepository.existsByEmail(registerReq.getUsername())) {
            logger.warn("user already exists");
            throw new ValidationException(String.format("Пользователь \"%s\" уже зарегистрирован!", registerReq.getUsername()));
        }

        User user = new User(registerReq.getUsername(), encoder.encode(registerReq.getPassword()), registerReq.getFirstName(),
                registerReq.getLastName(), registerReq.getPhone(), role);
        logger.info(user.getRole().getAuthority());
        logger.info(user.getRole().name());
        userRepository.save(user);
        return true;
    }
}
