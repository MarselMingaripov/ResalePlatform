package ru.min.resaleplatform.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "api for work with users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<UserDetails> findUserById(){
        return ResponseEntity.ok(userService.findUser());
    }
}
