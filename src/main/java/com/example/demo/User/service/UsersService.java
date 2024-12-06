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

    public Users getUserPUUID(String username, String tag) {
        Optional<Users> user = usersRepository.findByUsernameAndTag(username, tag);
        //user가 존재할 경우 불러오기
        //user가 존재하지 않을경우 삽입
        if(user.isEmpty()) {
            String riotpuuid = riotAPI.getPUUIDFromRiotAPI(username, tag);
            user = Optional.ofNullable(Users.toEntity(username, tag, riotpuuid));
            usersRepository.save(user.get());
        }

        return user.get();
    }

    public Users saveUserByPuuid(String puuid, String username, String tag) {
        Optional<Users> user = usersRepository.findUserByPuuid(puuid);
        if(user.isEmpty()) {
            user = Optional.ofNullable(Users.toEntity(username, tag, puuid));
            usersRepository.save(user.get());
        }
        return user.get();
    }
}
