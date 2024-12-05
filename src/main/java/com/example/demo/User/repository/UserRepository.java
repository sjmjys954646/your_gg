package com.example.demo.User.repository;

import com.example.demo.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select v from User v where v.username = :username and v.tag = :tag")
    Optional<User> findByUsernameAndTag(@Param("nickname") String username, @Param("tag") String tag);
}
