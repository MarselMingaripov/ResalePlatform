package ru.min.resaleplatform.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.repository.UserRepository;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {
    /*private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);

    }*/

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);

        return new MyUserDetails(user);
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = getUserByUsername(userDetails.getUsername());

        user.setPassword(newPassword);

        MyUserDetails updatedUserDetails = new MyUserDetails(userRepository.save(user));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(updatedUserDetails, null, updatedUserDetails.getAuthorities())
        );

        return updatedUserDetails;
    }

    private User getUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Пользователь с email: \"%s\" не найден", username)));
    }

}
