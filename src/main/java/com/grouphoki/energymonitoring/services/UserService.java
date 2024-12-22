package com.grouphoki.energymonitoring.services;

import com.grouphoki.energymonitoring.dto.UserDto;
import com.grouphoki.energymonitoring.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUser();
    UserDto findUserById(Long userId);
    Optional<User> findByUsername(String username);

    User saveUser(UserDto userDto);
    void updateUser(UserDto userDto);
    void deleteUser(Long userId);
}
