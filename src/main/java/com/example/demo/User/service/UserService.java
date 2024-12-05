package com.example.demo.User.service;

import com.example.demo.User.dto.UserResponse;
import com.example.demo.User.entity.Users;
import com.example.demo.User.repository.UserRepository;
import com.example.demo.Utils.RiotAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService{
    private final UserRepository userRepository;
    private final RiotAPI riotAPI;

    public UserResponse.UserResponseDTO getUserPUUID(String username, String tag) {
        Optional<Users> user = findUser(username, tag);
        UserResponse.UserResponseDTO userResponseDTO = null;
        //user가 존재할 경우 불러오기
        //user가 존재하지 않을경우 삽입
        if(user.isPresent()) {
            userResponseDTO = new UserResponse.UserResponseDTO(user.get());
        }
        else{
            String riotpuuid = riotAPI.getPUUIDFromRiotAPI(username, tag);
            Users newUsers = Users.toEntity(username, tag, riotpuuid);
            userRepository.save(newUsers);
            userResponseDTO = new UserResponse.UserResponseDTO(newUsers);
        }

        //userResponseDTO가 null일경우 에러처리

        return userResponseDTO;
    }

    private Optional<Users> findUser(String username, String tag)
    {
        return userRepository.findByUsernameAndTag(username, tag);
    }

}
