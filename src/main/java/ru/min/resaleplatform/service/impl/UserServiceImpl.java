package ru.min.resaleplatform.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.NewPasswordDto;
import ru.min.resaleplatform.model.dto.UserDto;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.security.dto.Role;
import ru.min.resaleplatform.service.UserService;
import ru.min.resaleplatform.service.ValidationService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(User user) {
        if (!validationService.validate(user)) {
            return null;
        } else {
            return userRepository.save(user);
        }
    }

    @Override
    public UserDto findUser() {
        User user = getCurrentUser();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        logger.info(userDto.toString());
        return userDto;
    }

    @Override
    public void updatePassword(NewPasswordDto newPasswordDto){
        logger.info(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if (encoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())){
            user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
            logger.info(userRepository.findByEmail(SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getName()).get()
                            .getPassword());
            userRepository.save(user);
        } else {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto){
        User user = getCurrentUser();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setImage(userDto.getImage());
        userRepository.save(user);
        logger.info(user.toString());
        return modelMapper.map(user, UserDto.class);
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
    public void updateImage(MultipartFile image) {
        User user = getCurrentUser();
        try{
            if (!image.isEmpty()){
                String fileName = " " + user.getEmail() + "_" +
                        image.getOriginalFilename();
                image.transferTo(new File("C:\\Users\\User\\Documents\\IdeaProjects\\ResalePlatform\\src\\main\\resources\\static\\images" + fileName));
                user.setImage("C:\\Users\\User\\Documents\\IdeaProjects\\ResalePlatform\\src\\main\\resources\\images" + fileName);
                userRepository.save(user);
                logger.info(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User getCurrentUser(){
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }
}
