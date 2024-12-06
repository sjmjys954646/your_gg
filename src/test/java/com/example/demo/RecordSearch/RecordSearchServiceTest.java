package com.example.demo.RecordSearch;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.RecordSearch.repository.RecordUsersRepository;
import com.example.demo.RecordSearch.repository.RecordsRepository;
import com.example.demo.RecordSearch.repository.UserRecentRecordRepository;
import com.example.demo.RecordSearch.service.RecordsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class RecordSearchServiceTest {
    @Autowired
    RecordsRepository recordsRepository;
    @Autowired
    RecordsService recordsService;
    @Autowired
    RecordUsersRepository recordUsersRepository;
    @Autowired
    UserRecentRecordRepository userRecentRecordRepository;

    @Test
    @DisplayName("유저의 최근 마지막 매치 데이터")
    void getRecordsSuccessTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";

        RecordsResponse.RecordSearchResponseDTO response = recordsService.getRecords(username, tag);

        assertThat(response.getRecords().getMatchId()).isEqualTo("KR_7358346186");
    }

    @Test
    @DisplayName("유저의 최근 마지막 매치 데이터 조회 후 같은 팀 유저의 마지막 데이터 조회")
    void getRecordsSuccessTest2(){
        //given
        String username = "더유자소전";
        String tag = "KR1";
        String username2 = "asdfs";

        RecordsResponse.RecordSearchResponseDTO response1 = recordsService.getRecords(username, tag);
        RecordsResponse.RecordSearchResponseDTO response2 = recordsService.getRecords(username2, tag);

        assertThat(response1.getRecords().getMatchId()).isEqualTo(response2.getRecords().getMatchId());
    }

    @Test
    @DisplayName("유저의 최근 마지막 매치 캐싱 확인")
    void getRecordsSuccessCachingTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";

        //api로 불러온 데이터
        RecordsResponse.RecordSearchResponseDTO response1 = recordsService.getRecords(username, tag);
        //캐싱된 데이터
        RecordsResponse.RecordSearchResponseDTO response2 = recordsService.getRecords(username, tag);
        assertThat(response1.getRecords().getMatchId()).isEqualTo(response2.getRecords().getMatchId());
        assertThat(response1.getRecords().getBluewin()).isEqualTo(response2.getRecords().getBluewin());
        assertThat(response1.getRecords().getBlueKill()).isEqualTo(response2.getRecords().getBlueKill());
        assertThat(response1.getRecords().getRedKill()).isEqualTo(response2.getRecords().getRedKill());
        assertThat(response1.getRecords().getEndTime()).isEqualTo(response2.getRecords().getEndTime());
        assertThat(response1.getRecords().getGameTime()).isEqualTo(response2.getRecords().getGameTime());

        assertEquals(response1.getRedTeam().equals(response2.getRedTeam()), true);
        assertEquals(response1.getBlueTeam().equals(response2.getBlueTeam()), true);
    }

    @Test
    @DisplayName("유저의 최근 마지막 매치 데이터 패치 테스트")
    void patchRecordsSuccessTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";

        RecordsResponse.RecordSearchResponseDTO response = recordsService.patchRecords(username, tag);

        assertThat(response.getRecords().getMatchId()).isEqualTo("KR_7358346186");
    }
}
