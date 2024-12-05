package com.example.demo.RecordSearch.service;

import com.example.demo.RecordSearch.dto.RecordSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class RecordSearchService {
    public RecordSearchResponse.RecordSearchResponseDTO getRecordSearch(String id, String tag) {

        return null;
    }
}
