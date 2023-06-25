package ru.min.resaleplatform.security.service;

import ru.min.resaleplatform.security.dto.RegisterReq;

public interface UserCreatorService {

    void createUser(RegisterReq registerReq);
}
