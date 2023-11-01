package com.loveacamp.promotions.repositories;


import com.loveacamp.promotions.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsById(Long id);

    @Query("""
           SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END 
           FROM User u WHERE u.username = :username AND u.id <> :id
    """)
    boolean existsByUsernameNotId(@Param("username") String username, @Param("id") Long id);
}
