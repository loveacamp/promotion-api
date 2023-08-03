package com.loveacamp.promotions.entities;

import com.loveacamp.promotions.enums.UserLevel;
import jakarta.persistence.*;


@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false ,length = 255)
    private String username;

    @Column(name = "PASSWORD", nullable = false ,length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEVEL", nullable = false)
    private UserLevel level;

    public User() {
    }

    public User(Long id, String username, String password, UserLevel level) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;

        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;

        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserLevel getLevel() {
        return level;
    }

    public User setLevel(UserLevel level) {
        this.level = level;

        return this;
    }
}
