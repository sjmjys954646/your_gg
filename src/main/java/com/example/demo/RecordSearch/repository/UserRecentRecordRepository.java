package com.example.demo.RecordSearch.repository;

import com.example.demo.RecordSearch.entity.UserRecentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRecentRecordRepository extends JpaRepository<UserRecentRecord, Long> {
    @Query(value = "select v from UserRecentRecord v where v.user.id = :id")
    Optional<UserRecentRecord> findMyRecentRecordSearch(@Param("id") Long id);
}
