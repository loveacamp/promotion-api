package com.loveacamp.promotions.dto.requests;

import com.loveacamp.promotions.enums.UserLevel;
import jakarta.validation.constraints.*;

public class UserRequestDto {
    @NotBlank
    @Size(min = 3, max = 250)
    private String username;

    @NotBlank
    @Size(min = 3, max = 250)
    private String password;

    @NotNull
    private UserLevel level;

    public UserRequestDto() {
    }

    public UserRequestDto(String username, String password, UserLevel level) {
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
    }
}
