package com.example.demo.User.controller;

import com.example.demo.User.dto.UsersResponse;
import com.example.demo.User.service.UsersService;
import com.example.demo.Utils.ApiResponse;
import com.example.demo.Utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersRestController {
    private final UsersService usersService;

    @GetMapping("getPUUID")
    public ResponseEntity<ApiResponse<UsersResponse.UserResponseDTO>> getUser(
            @RequestParam("username") String username, @RequestParam("tag") String tag
    )
    {
        UsersResponse.UserResponseDTO response = usersService.getUserPUUID(username, tag);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.GET_SUCCESS, response));
    }
}
