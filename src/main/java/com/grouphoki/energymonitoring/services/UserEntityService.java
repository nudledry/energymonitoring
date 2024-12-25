package com.grouphoki.energymonitoring.services;


import com.grouphoki.energymonitoring.dto.RegistrationDto;
import com.grouphoki.energymonitoring.models.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public interface UserEntityService {
    void saveUserEntity(RegistrationDto registrationDto);
    void updateUser(@Valid UserEntity userEntity);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(@NotEmpty String username);

}
