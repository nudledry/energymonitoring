package com.grouphoki.energymonitoring.mapper;

import com.grouphoki.energymonitoring.dto.UserDto;
import com.grouphoki.energymonitoring.models.User;

public class UserMapper {
    public static User mapToUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .createdAt(userDto.getCreatedAt())
                .updatedAt(userDto.getUpdatedAt())
                .build();
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return userDto;
    }
}
