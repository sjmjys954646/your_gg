package com.example.demo.RecordSearch.controller;


import com.example.demo.Utils.ApiResponse;
import com.example.demo.Utils.SuccessCode;
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
        //TODO 데이터 있는게 있으면 캐시데이터를 불러오고 아니면 라이엇 API를 불러와야함
        RecordSearchDTO response = recordSearchService.getRecordSearch(id, tag);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.GET_SUCCESS, response));
    }
}
