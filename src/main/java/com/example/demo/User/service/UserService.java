package com.example.demo.User.service;

import com.example.demo.User.dto.UserResponse;
import com.example.demo.User.entity.User;
import com.example.demo.User.repository.UserRepository;
import com.example.demo.Utils.BaseTime;
import com.example.demo.Utils.Exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.demo.Utils.ErrorCode.USER_NOT_FOUND;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService{
    private final UserRepository userRepository;

    public UserResponse.UserResponseDTO getUserPUUID(String username, String tag) {
        Optional<User> user = findUser(username, tag);
        UserResponse.UserResponseDTO userResponseDTO = null;
        //user가 존재할 경우 불러오기
        //user가 존재하지 않을경우 삽입
        if(user.isPresent()) {
            userResponseDTO = new UserResponse.UserResponseDTO(user.get());
        }
        else{
            String riotpuuid = "abcd";
            User newUser = User.toEntity(username, tag, riotpuuid);
            userRepository.save(newUser);
            userResponseDTO = new UserResponse.UserResponseDTO(newUser);
        }

        return userResponseDTO;
    }

    private Optional<User> findUser(String username, String tag)
    {
        return userRepository.findByUsernameAndTag(username, tag);
    }

}
