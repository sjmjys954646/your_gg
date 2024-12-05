package com.example.demo.User.controller;

import com.example.demo.Utils.ApiResponse;
import com.example.demo.Utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("getPUUID/id={id}/tag={tag}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(
            @PathVariable("id") String id,
            @PathVariable("tag") String tag
    )
    {
        UserResponse response = userService.getUserPUUID(id, tag);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.GET_SUCCESS, response));
    }
}
