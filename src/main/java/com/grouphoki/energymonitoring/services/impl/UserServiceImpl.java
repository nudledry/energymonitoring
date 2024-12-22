package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.UserDto;
import com.grouphoki.energymonitoring.models.User;
import com.grouphoki.energymonitoring.repository.RoleRepository;
import com.grouphoki.energymonitoring.repository.UserRepository;
import com.grouphoki.energymonitoring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.grouphoki.energymonitoring.mapper.UserMapper.mapToUser;
import static com.grouphoki.energymonitoring.mapper.UserMapper.mapToUserDto;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        return mapToUserDto(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User saveUser(UserDto userDto) {
        User user = mapToUser(userDto);
        return userRepository.save(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User updateddUser = mapToUser(userDto);
        userRepository.save(updateddUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
