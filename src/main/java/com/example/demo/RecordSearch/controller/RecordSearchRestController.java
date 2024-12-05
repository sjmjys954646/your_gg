package com.example.demo.RecordSearch.controller;


import com.example.demo.RecordSearch.dto.RecordSearchResponse;
import com.example.demo.RecordSearch.service.RecordSearchService;
import com.example.demo.Utils.ApiResponse;
import com.example.demo.Utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecordSearchRestController {
    private final RecordSearchService recordSearchService;

    @GetMapping("getRecordSearch")
    public ResponseEntity<ApiResponse<RecordSearchResponse.RecordSearchResponseDTO>> getRecordSearch(
            @RequestParam("username") String username, @RequestParam("tag") String tag
    )
    {
        //TODO 데이터 있는게 있으면 캐시데이터를 불러오고 아니면 라이엇 API를 불러와야함
        RecordSearchResponse.RecordSearchResponseDTO response = recordSearchService.getRecordSearch(username, tag);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.GET_SUCCESS, response));
    }

    @PatchMapping("patchRecordSearch")
    public ResponseEntity<ApiResponse<RecordSearchResponse.RecordSearchResponseDTO>> patchRecordSearch(
            @RequestParam("username") String username, @RequestParam("tag") String tag
    )
    {
        //TODO 갱신 시간에 따라 쿨타임 주기
        //TODO 라이엇 API 요청으로 데이터 갱신
        RecordSearchResponse.RecordSearchResponseDTO response = recordSearchService.getRecordSearch(username, tag);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.GET_SUCCESS, response));
    }

}
