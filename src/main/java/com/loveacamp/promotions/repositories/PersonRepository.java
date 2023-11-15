package com.loveacamp.promotions.repositories;

import com.loveacamp.promotions.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository  extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Person p  WHERE p.email = :email AND p.id <> :id        
    """)
    boolean existsByEmailNotId(@Param("email") String email, @Param("id") long id);
}
