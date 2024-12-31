package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.RegistrationDto;
import com.grouphoki.energymonitoring.exception.ResourceNotFoundException;
import com.grouphoki.energymonitoring.models.Role;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.repository.RoleRepository;
import com.grouphoki.energymonitoring.repository.UserEntityRepository;
import com.grouphoki.energymonitoring.services.UserEntityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
    public void saveAdminUserEntity(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        Role role = roleRepository.findByName("ADMIN");
        userEntity.setRoles(Arrays.asList(role));
        userEntityRepository.save(userEntity);
    }

    @Override
    public void updateUserEntity(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }

    @Override
    public void deleteUserEntity(UserEntity userEntity) {
        userEntity.getRoles().clear();
        userEntityRepository.save(userEntity);
        userEntityRepository.deleteById(userEntity.getId());
    }

    @Override
    public void saveTarget(UserEntity currentUser) {
        userEntityRepository.save(currentUser);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    @Override
    public UserEntity findById(Long userId) {
        return userEntityRepository.findById(userId).get();
    }

    @Override
    public List<UserEntity> getAll() {
        return userEntityRepository.findAll();
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return userEntityRepository.findByUsername(currentUsername);
    }
}
