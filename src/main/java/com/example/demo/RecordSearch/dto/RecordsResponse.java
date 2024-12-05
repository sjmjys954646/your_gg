package com.example.demo.RecordSearch.dto;

import com.example.demo.RecordSearch.entity.RecordUsers;
import com.example.demo.RecordSearch.entity.Records;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

public class RecordsResponse {
    @Getter
    public static class RecordSearchResponseDTO{
        //enum형처리
        private String gameType;
        private String gameTime;
        private int blueKill;
        private int redKill;
        ArrayList<UsersInfoDTO> blueTeam;
        ArrayList<UsersInfoDTO> redTeam;

        @Builder
        public RecordSearchResponseDTO(Records records, ArrayList<RecordUsers> blueTeam, ArrayList<RecordUsers> redTeam) {}

        @Getter
        public static class UsersInfoDTO{
            private int deal;
            private String champion;

            @Builder
            public UsersInfoDTO(RecordUsers recordUsers){
                this.deal = recordUsers.getDeal();
                this.champion = recordUsers.getChampion();
            }
        }
    }
}
