package com.example.demo.RecordSearch.controller;


import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.RecordSearch.service.RecordsService;
import com.example.demo.Utils.ApiResponse;
import com.example.demo.Utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecordsRestController {
    private final RecordsService recordsService;

    @GetMapping("getRecords")
    public ResponseEntity<ApiResponse<RecordsResponse.RecordSearchResponseDTO>> getRecords(
            @RequestParam("username") String username, @RequestParam("tag") String tag
    )
    {
        RecordsResponse.RecordSearchResponseDTO response = recordsService.getRecords(username, tag);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.GET_SUCCESS, response));
    }

    @PatchMapping("patchRecords")
    public ResponseEntity<ApiResponse<RecordsResponse.RecordSearchResponseDTO>> patchRecords(
            @RequestParam("username") String username, @RequestParam("tag") String tag
    )
    {
        //TODO 갱신 시간에 따라 쿨타임 주기
        //TODO 라이엇 API 요청으로 데이터 갱신
        RecordsResponse.RecordSearchResponseDTO response = recordsService.patchRecords(username, tag);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.GET_SUCCESS, response));
    }

}
