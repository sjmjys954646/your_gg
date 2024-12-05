package com.example.demo.RecordSearch.repository;

import com.example.demo.RecordSearch.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecordsRepository extends JpaRepository<Records, Long> {
}
