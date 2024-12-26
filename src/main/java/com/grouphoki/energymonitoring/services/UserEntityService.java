package com.grouphoki.energymonitoring.services;


import com.grouphoki.energymonitoring.dto.RegistrationDto;
import com.grouphoki.energymonitoring.models.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface UserEntityService {
    void saveUserEntity(RegistrationDto registrationDto);
    void updateUserEntity(@Valid UserEntity userEntity);
    void deleteUserEntity(Long userId);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(@NotEmpty String username);
    UserEntity findById(Long userId);

    List<UserEntity> getAll();
}
