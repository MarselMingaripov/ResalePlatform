package ru.min.resaleplatform.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.security.dto.RegisterReq;
import ru.min.resaleplatform.security.dto.Role;

import javax.validation.ValidationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new UserDetailsImpl(user);
    }

    public void createUser(RegisterReq registerReq) {
        if (userRepository.existsByEmail(registerReq.getUsername())) {
            throw new ValidationException(String.format("Пользователь \"%s\" уже зарегистрирован!", registerReq.getUsername()));
        }
        User user = new User();
        user.setFirstName(registerReq.getFirstName());
        user.setLastName(registerReq.getLastName());
        user.setEmail(registerReq.getUsername());
        user.setPassword(encoder.encode(registerReq.getPassword()));
        user.setPhone(registerReq.getPhone());
        user.setRole(registerReq.getRole());
        userRepository.save(user);
        log.debug("User successfully saved = {}", user);
    }
}
