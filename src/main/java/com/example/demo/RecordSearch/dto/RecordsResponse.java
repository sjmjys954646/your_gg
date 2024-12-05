package com.example.demo.RecordSearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;

public class RecordsResponse {
    @Getter
    public static class RecordSearchResponseDTO{
        RecordInfoDTO records;
        ArrayList<UsersInfoDTO> blueTeam;
        ArrayList<UsersInfoDTO> redTeam;

        @Builder
        public RecordSearchResponseDTO(RecordInfoDTO records, ArrayList<UsersInfoDTO> blueTeam, ArrayList<UsersInfoDTO> redTeam) {
            this.records = records;
            this.blueTeam = blueTeam;
            this.redTeam = redTeam;
        }

        @ToString
        @Getter
        public static class RecordInfoDTO{
            private String matchId;
            private String gameType;
            private String gameTime;
            private int blueKill;
            private int redKill;
            private Date endTime;
            private boolean bluewin;

            @Builder
            public RecordInfoDTO(String matchId, String gameType, String gameTime, int blueKill, int redKill,Date endTime ,boolean bluewin) {
                this.matchId = matchId;
                this.gameType = gameType;
                this.gameTime = gameTime;
                this.blueKill = blueKill;
                this.redKill = redKill;
                this.endTime = endTime;
                this.bluewin = bluewin;
            }
        }

        @ToString
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
