package com.example.demo.User;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.RecordSearch.entity.RecordUsers;
import com.example.demo.RecordSearch.entity.Records;
import com.example.demo.RecordSearch.entity.UserRecentRecord;
import com.example.demo.RecordSearch.repository.RecordUsersRepository;
import com.example.demo.RecordSearch.repository.RecordsRepository;
import com.example.demo.RecordSearch.repository.UserRecentRecordRepository;
import com.example.demo.RecordSearch.service.RecordsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class RecordSearchRepositoryTest {
    @Autowired
    RecordsRepository recordsRepository;
    @Autowired
    RecordsService recordsService;
    @Autowired
    RecordUsersRepository recordUsersRepository;
    @Autowired
    UserRecentRecordRepository userRecentRecordRepository;

    @Test
    @DisplayName("recordsRepository findByMatchId Test")
    void getRecordsSuccessRepositoryTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";
        String matchId = "KR_7358346186";

        RecordsResponse.RecordSearchResponseDTO recordSearchResponseDTO = recordsService.getRecords(username, tag);

        Optional<Records> records = recordsRepository.findByMatchId(matchId);

        assertThat(records.get().getMatchId()).isEqualTo(recordSearchResponseDTO.getRecords().getMatchId());
    }

    @Test
    @DisplayName("recordUsersRepository findRecrodUsersByRecordId Test")
    void getRecordsUsersSuccessRepositoryTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";
        String matchId = "KR_7358346186";

        RecordsResponse.RecordSearchResponseDTO recordSearchResponseDTO = recordsService.getRecords(username, tag);

        ArrayList<RecordUsers> recordUsers = recordUsersRepository.findRecrodUsersByRecordId(1L);

        assertThat(recordUsers.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("userRecentRecordRepository findMyRecentRecordSearch Test")
    void getUserRecentRecordsSuccessRepositoryTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";
        String matchId = "KR_7358346186";

        RecordsResponse.RecordSearchResponseDTO recordSearchResponseDTO = recordsService.getRecords(username, tag);


        Optional<UserRecentRecord> recordUsers = userRecentRecordRepository.findMyRecentRecordSearch(1L);

        assertThat(recordUsers.get().getRecord().getMatchId()).isEqualTo(recordSearchResponseDTO.getRecords().getMatchId());
        assertThat(recordUsers.get().getRecord().getBlueKill()).isEqualTo(recordSearchResponseDTO.getRecords().getBlueKill());
        assertThat(recordUsers.get().getRecord().getRedKill()).isEqualTo(recordSearchResponseDTO.getRecords().getRedKill());
        assertThat(recordUsers.get().getRecord().getEndTime()).isEqualTo(recordSearchResponseDTO.getRecords().getEndTime());
        assertThat(recordUsers.get().getRecord().getGameTime()).isEqualTo(recordSearchResponseDTO.getRecords().getGameTime());

    }
}
