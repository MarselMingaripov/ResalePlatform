package ru.min.resaleplatform.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.NewPasswordDto;
import ru.min.resaleplatform.model.dto.UserDto;
import ru.min.resaleplatform.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> findUserById(){
        return ResponseEntity.ok(userService.findUser());
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto){
        userService.updatePassword(newPasswordDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateImage(@RequestParam(value = "image") MultipartFile multipartFile){
        userService.updateImage(multipartFile);
        return ResponseEntity.ok().build();
    }
}
