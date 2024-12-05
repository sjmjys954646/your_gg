package com.example.demo.User.service;

import com.example.demo.User.dto.UsersResponse;
import com.example.demo.User.entity.Users;
import com.example.demo.User.repository.UsersRepository;
import com.example.demo.Utils.RiotAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final RiotAPI riotAPI;

    public UsersResponse.UserResponseDTO getUserPUUID(String username, String tag) {
        Optional<Users> user = usersRepository.findByUsernameAndTag(username, tag);
        UsersResponse.UserResponseDTO userResponseDTO = null;
        //user가 존재할 경우 불러오기
        //user가 존재하지 않을경우 삽입
        if(user.isPresent()) {
            userResponseDTO = new UsersResponse.UserResponseDTO(user.get());
        }
        else{
            String riotpuuid = riotAPI.getPUUIDFromRiotAPI(username, tag);
            Users newUsers = Users.toEntity(username, tag, riotpuuid);
            usersRepository.save(newUsers);
            userResponseDTO = new UsersResponse.UserResponseDTO(newUsers);
        }

        //userResponseDTO가 null일경우 에러처리

        return userResponseDTO;
    }

    public void saveUserByPuuid(String puuid, String username, String tag) {
        Optional<Users> user = usersRepository.findUserByPuuid(puuid);
        if(user.isEmpty()) {
            Users newUsers = Users.toEntity(username, tag, puuid);
            usersRepository.save(newUsers);
        }
    }
}
