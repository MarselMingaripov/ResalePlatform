package ru.min.resaleplatform.service.impl.mapping;

import org.modelmapper.PropertyMap;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.UserDto;

public class UserDtoToUserMapService extends PropertyMap<UserDto, User> {
    @Override
    protected void configure() {
        map().setFirstName(source.getFirstName());
        map().setLastName(source.getLastName());
        map().setPhone(source.getPhone());
        map().setEmail(destination.getEmail());
        map().setRole(destination.getRole());
        map().setImage(destination.getImage());
        map().setPassword(destination.getPassword());
        map().setId(destination.getId());

    }
}
