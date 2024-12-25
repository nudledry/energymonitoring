package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.RegistrationDto;
import com.grouphoki.energymonitoring.models.Role;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.repository.RoleRepository;
import com.grouphoki.energymonitoring.repository.UserEntityRepository;
import com.grouphoki.energymonitoring.services.UserEntityService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class UserEntityServiceImpl implements UserEntityService {
    private UserEntityRepository userEntityRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserEntityServiceImpl(RoleRepository roleRepository, UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUserEntity(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        Role role = roleRepository.findByName("USER");
        userEntity.setRoles(Arrays.asList(role));
        userEntityRepository.save(userEntity);

    }

    @Override
    public void updateUser(UserEntity userEntity) {
        UserEntity user = userEntityRepository.findById(userEntity.getId()).get();
        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setRoles(userEntity.getRoles());
        user.setTarget(userEntity.getTarget());
        userEntityRepository.save(userEntity);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

}
