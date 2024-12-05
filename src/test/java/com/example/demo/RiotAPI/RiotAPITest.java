package com.example.demo.RiotAPI;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.User.dto.UsersResponse;
import com.example.demo.Utils.RiotAPI;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class RiotAPITest {
    @Autowired
    RiotAPI riotAPI;

    @Test
    @DisplayName("matchId로 해당 매치업 정보를 받아오는 API를 확인합니다.")
    void getUserPUUIDSuccessTest(){
        //given
        String matchId = "KR_7353085063";

        //when
        RecordsResponse.RecordSearchResponseDTO recordSearchResponseDTO = riotAPI.getRecordsByMatchId(matchId);

        System.out.println("ASDF");
        System.out.println(recordSearchResponseDTO.getRecords().toString());
        for(RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO i : recordSearchResponseDTO.getBlueTeam()){
            System.out.println(i.toString());
        }
        for(RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO i : recordSearchResponseDTO.getRedTeam()){
            System.out.println(i.toString());
        }
    }

    @Test
    @DisplayName("username, Tag로 PUUID를 받아오는 API를 확인합니다.")
    void getPUUIDFromRiotAPISuccessTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";

        //when
        String ret = riotAPI.getPUUIDFromRiotAPI(username, tag);

        assertThat(ret).isEqualTo("WM-w8SKG3RATuOSi41mOugd7lMWhjMHuGfGjvwrTzDD76xIs5uvsrFbepjrzgM2AMlSXDG921wCNIg");
    }

    @Test
    @DisplayName("username, Tag로 PUUID를 받아오는 API를 확인합니다.")
    void getMatchIdByQueueFromRiotAPISuccessTest(){
        //given
        String puuid = "WM-w8SKG3RATuOSi41mOugd7lMWhjMHuGfGjvwrTzDD76xIs5uvsrFbepjrzgM2AMlSXDG921wCNIg";

        //when
        String ret = riotAPI.getMatchIdByQueueFromRiotAPI(puuid, "420");

        assertThat(ret).isEqualTo("KR_7353085063");
    }
}
