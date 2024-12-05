package com.example.demo.RecordSearch.service;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.RecordSearch.entity.Records;
import com.example.demo.RecordSearch.repository.RecordsRepository;
import com.example.demo.User.service.UsersService;
import com.example.demo.Utils.RiotAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class RecordsService {
    private final UsersService usersService;
    private final RecordsRepository recordsRepository;
    private final RiotAPI riotAPI;

    public RecordsResponse.RecordSearchResponseDTO getRecords(String username, String tag) {
        //id, tag에 맞는 puuid 를 가져옵니다.
        String myPUUID = usersService.getUserPUUID(username, tag).getPuuid();

        RecordsResponse.RecordSearchResponseDTO userResponseDTO = null;
        Optional<Records> records = recordsRepository.findMyRecentRecordSearch(myPUUID);

        if(records.isPresent()) {
            userResponseDTO = new UsersResponse.UserResponseDTO(user.get());
        }
        else{
            String riotpuuid = riotAPI.getPUUIDFromRiotAPI(username, tag);
            Users newUsers = Users.toEntity(username, tag, riotpuuid);
            usersRepository.save(newUsers);
            userResponseDTO = new UsersResponse.UserResponseDTO(newUsers);
        }

        return null;
    }
}
