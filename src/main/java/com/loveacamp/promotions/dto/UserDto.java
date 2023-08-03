package com.loveacamp.promotions.dto;

import com.loveacamp.promotions.entities.User;
import com.loveacamp.promotions.enums.UserLevel;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private Long id;
    private String username;

    private UserLevel level;

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;

        return this;
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;

        return this;
    }

    public UserLevel getLevel() {
        return level;
    }

    public UserDto setLevel(UserLevel level) {
        this.level = level;

        return this;
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        return userDto
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setLevel(user.getLevel());
    }

    public static List<UserDto> toDto(List<User> users) {
        return users.stream().map(UserDto::toDto).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("UserDto({id:%s, username:%s, level:%s})",
                this.getId(),
                this.getUsername(),
                this.getLevel()
        );
    }
}