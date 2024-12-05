package com.example.demo.User.service;

import com.example.demo.User.dto.UserResponse;
import com.example.demo.User.entity.User;
import com.example.demo.Utils.BaseTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService{
    private final UserRepository userRepository;

    public UserResponse.UserResponseDTO getUserPUUID(String username, String tag) {
        //private User
    }
}
