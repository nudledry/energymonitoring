package com.grouphoki.energymonitoring.services;


import com.grouphoki.energymonitoring.dto.RegistrationDto;
import com.grouphoki.energymonitoring.dto.UserDto;
import com.grouphoki.energymonitoring.models.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface UserEntityService {
    void saveUserEntity(RegistrationDto registrationDto);
    void saveAdminUserEntity(RegistrationDto registrationDto);
    void updateUserEntity(@Valid UserEntity userEntity);
    void deleteUserEntity(UserEntity userEntity);
    void saveTarget(UserEntity currentUser);

    UserEntity findByEmail(String email);
    UserEntity findByUsername(@NotEmpty String username);
    UserEntity findById(Long userId);

    List<UserEntity> getAll();
}
