package ru.min.resaleplatform.service;

import ru.min.resaleplatform.dto.RegisterReq;
import ru.min.resaleplatform.dto.Role;

public interface AuthService {

    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
