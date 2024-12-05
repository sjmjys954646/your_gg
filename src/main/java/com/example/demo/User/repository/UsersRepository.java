package com.example.demo.User.repository;

import com.example.demo.User.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query(value = "select v from Users v where v.username = :username and v.tag = :tag")
    Optional<Users> findByUsernameAndTag(@Param("username") String username, @Param("tag") String tag);
}
