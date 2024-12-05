package com.example.demo.RecordSearch.service;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.RecordSearch.entity.Records;
import com.example.demo.RecordSearch.entity.UserRecentRecord;
import com.example.demo.RecordSearch.repository.RecordUsersRepository;
import com.example.demo.RecordSearch.repository.RecordsRepository;
import com.example.demo.RecordSearch.repository.UserRecentRecordRepository;
import com.example.demo.User.service.UsersService;
import com.example.demo.Utils.RiotAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class RecordsService {
    private final UsersService usersService;
    private final RecordsRepository recordsRepository;
    private final RecordUsersRepository recordUsersRepository;
    private final UserRecentRecordRepository userRecentRecordRepository;
    private final RiotAPI riotAPI;

    //420 : 솔로랭크 440 : 자유랭크 490 : 일반게임
    private static final ArrayList<String> queueList =  new ArrayList<>(List.of("420", "440", "490"));

    public RecordsResponse.RecordSearchResponseDTO getRecords(String username, String tag) {
        //id, tag에 맞는 puuid 를 가져옵니다.
        String myPUUID = usersService.getUserPUUID(username, tag).getPuuid();

        RecordsResponse.RecordSearchResponseDTO recordSearchResponseDTO = null;

        //유저의 최근 Record를 찾아옵니다.
        Optional<UserRecentRecord> records = userRecentRecordRepository.findMyRecentRecordSearch(myPUUID);

        //record에 있는 matchId로 참가자들의 정보를 가져옵니다.
        //최근 record가 없을 경우 RiotAPI를 통해 유저의 가장 최근 경기를 저장합니다.
        if(records.isPresent()) {
            //recordSearchResponseDTO = new RecordsResponse.RecordSearchResponseDTO();
        }
        else{
            ArrayList<String> recentRecordHubo = new ArrayList<>();
            for (String queue : queueList) {
                Optional.ofNullable(riotAPI.getMatchIdByQueueFromRiotAPI(myPUUID, queue))
                        .ifPresent(recentRecordHubo::add);
            }

            //오류 최근 기록이 없음
            if(recentRecordHubo.isEmpty()) {
                //오류처리
            }

            //최근 경기 후보 중 가장 최근에 끝난 경기를 찾아 변환한다.
        }

        return null;
    }
}
