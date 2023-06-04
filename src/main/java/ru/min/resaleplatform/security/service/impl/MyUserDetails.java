package ru.min.resaleplatform.security.service.impl;
import ru.min.resaleplatform.model.User;

import java.util.List;

public class MyUserDetails extends org.springframework.security.core.userdetails.User {

    private final int id;

    public MyUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id = user.getId();
    }

    @Override
    public void eraseCredentials() {
    }

    public int getId() {
        return id;
    }
}
