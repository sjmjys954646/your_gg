package com.example.demo.RecordSearch.repository;

import com.example.demo.RecordSearch.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepository extends JpaRepository<Records, Long> {
}
