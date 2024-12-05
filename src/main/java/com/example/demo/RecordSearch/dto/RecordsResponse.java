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
        public RecordSearchResponseDTO(Records records, ArrayList<UsersInfoDTO> blueTeam, ArrayList<UsersInfoDTO> redTeam) {}

        @Getter
        public static class UsersInfoDTO{
            private int damageToChampion;
            private String champion;
            private int kill;
            private int death;
            private int assist;

            @Builder
            public UsersInfoDTO(int damageToChampion, String champion, int kill, int death, int assist) {
                this.damageToChampion = damageToChampion;
                this.champion = champion;
                this.kill = kill;
                this.death = death;
                this.assist = assist;
            }
        }
    }
}
