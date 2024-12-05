package com.example.demo.RecordSearch.repository;

import com.example.demo.RecordSearch.entity.RecordUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface RecordUsersRepository extends JpaRepository<RecordUsers, Long> {
    @Query(value = "select v from RecordUsers v where v.record.id = :id")
    ArrayList<RecordUsers> findRecrodUsersByRecordId(@Param("id") Long id);
}
