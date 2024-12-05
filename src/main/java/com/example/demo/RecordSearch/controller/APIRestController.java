package com.example.demo.RecordSearch.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecordSearchRestController {
    private final RecordSearchService recordSearchService;

    @GetMapping("get/id={id}/tag={tag}")
    public ResponseEntity<ApiResponse<RecordSearchDTO>> getRecordSearch(
            @PathVariable("id") String id,
            @PathVariable("tag") String tag
            )
    {
        RecordSearchDTO recordSearchDTO = recordSearchService.getRecordSearch(id, tag);
        return recordSearchDTO;
    }
}
