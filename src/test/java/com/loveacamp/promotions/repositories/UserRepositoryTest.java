package com.loveacamp.promotions.repositories;

import com.loveacamp.promotions.entities.User;
import com.loveacamp.promotions.enums.UserLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends AbstractRepository {
    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("findById")
    public void givenUserWhenFindByIdThenProduct() {
        Long id = 1L;
        User user = createEntity("John Doe", "senha", UserLevel.USER);
        repository.save(user);

        Optional<User> result = repository.findById(id);

        assertTrue(result.isPresent());
        User foundUser = result.get();
        assertUser(foundUser, "John Doe", "senha", UserLevel.USER);
    }

    @Test
    @DisplayName("findByUsername")
    public void givenUserWhenFindByUsernameThenProduct() {
        Long id = 1L;
        User user = createEntity("Carla Doe", "123", UserLevel.ADMIN);
        repository.save(user);

        Optional<User> result = repository.findByUsername(user.getUsername());

        assertTrue(result.isPresent());
        User foundUser = result.get();
        assertUser(foundUser, "Carla Doe", "123", UserLevel.ADMIN);
    }

    @Test
    @DisplayName("existsByUsernameNotId")
    public void givenUserWhenExistsByUsernameNotIdThenProduct() {
        Long id = 1L;
        User user = createEntity("Carla Doe", "123", UserLevel.ADMIN);
        repository.save(user);

        boolean result = repository.existsByUsernameNotId(user.getUsername(), id);

        assertFalse(result);
    }

    @Test
    @DisplayName("findAll")
    public void givenProductWhenFindAllThenProduct() {
        repository.save(createEntity("John Doe", "123", UserLevel.USER));
        repository.save(createEntity("Carla Doe", "123", UserLevel.USER));
        repository.save(createEntity("Paulo Doe", "123", UserLevel.ADMIN));

        List<User> result = repository.findAll();

        assertEquals(result.size(), 3);
    }

    private void assertUser(User user, String username, String password,  UserLevel level) {
        assertEquals((Long) 1L, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(level, user.getLevel());
    }

    private User createEntity(String username, String password,  UserLevel level) {
        return new User(null, username, password, level);
    }
}