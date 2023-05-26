package ru.min.resaleplatform.security.service;

import ru.min.resaleplatform.security.dto.RegisterReq;
import ru.min.resaleplatform.security.dto.Role;

public interface AuthService {

    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
