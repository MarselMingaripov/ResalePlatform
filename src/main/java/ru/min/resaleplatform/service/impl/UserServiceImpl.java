package ru.min.resaleplatform.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.NewPasswordDto;
import ru.min.resaleplatform.model.dto.UserDto;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.service.ImageService;
import ru.min.resaleplatform.service.UserService;
import ru.min.resaleplatform.service.ValidationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${image.upload.path}")
    private String imageUploadPath;

    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final ImageService imageService;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        return userDto;
    }

    @Override
    public void updatePassword(NewPasswordDto newPasswordDto) {
        logger.info(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if (encoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
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
    public UserDto updateUser(UserDto userDto) {
        User user = getCurrentUser();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        logger.info(user.toString());
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void updateImage(MultipartFile image) throws IOException {

        User user = getCurrentUser();
        imageService.createDir(imageUploadPath, logger);
        logger.info(user.toString());
        logger.info(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        if (image.getContentType().startsWith("image/")) {
            String fileName = UUID.randomUUID() + "_" +
                    image.getOriginalFilename();
            image.transferTo(new File(imageUploadPath + fileName));
            user.setImage("/static/" + fileName);
            userRepository.save(user);
            logger.info(image.getContentType());
            logger.info(fileName);
        }
    }


    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }

    @Override
    public User findById(int id){
        return userRepository.findById(id).orElseThrow();
    }
}
