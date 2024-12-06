package com.example.demo.RecordSearch.dto;

import com.example.demo.RecordSearch.entity.RecordUsers;
import com.example.demo.RecordSearch.entity.Records;
import com.example.demo.RecordSearch.entity.UserRecentRecord;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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
            private Boolean bluewin;
            private int blueGold;
            private int redGold;
            private int maxDamage;

            @Builder
            public RecordInfoDTO(String matchId, String gameType, String gameTime, int blueKill, int redKill,Date endTime ,boolean bluewin, int blueGold, int redGold, int maxDamage) {
                this.matchId = matchId;
                this.gameType = gameType;
                this.gameTime = gameTime;
                this.blueKill = blueKill;
                this.redKill = redKill;
                this.endTime = endTime;
                this.bluewin = bluewin;
                this.blueGold = blueGold;
                this.redGold = redGold;
                this.maxDamage = maxDamage;
            }

            public static RecordInfoDTO MakeDTO(UserRecentRecord records) {
                Records record = records.getRecord();
                String matchId = record.getMatchId();
                String gameType = record.getGameType();
                String gameTime = record.getGameTime();
                int blueKill = record.getBlueKill();
                int redKill = record.getRedKill();
                Date endTime = record.getEndTime();
                boolean bluewin = record.isBluewin();
                int blueGold = record.getBlueGold();
                int redGold = record.getRedGold();
                int maxDamage = record.getMaxDamage();

                return RecordInfoDTO.builder()
                        .matchId(matchId)
                        .gameType(gameType)
                        .gameTime(gameTime)
                        .blueKill(blueKill)
                        .redKill(redKill)
                        .endTime(endTime)
                        .bluewin(bluewin)
                        .blueGold(blueGold)
                        .redGold(redGold)
                        .maxDamage(maxDamage)
                        .build();
            }
        }

        @ToString
        @Getter
        public static class UsersInfoDTO{
            private String puuid;
            private String username;
            private String tag;
            private int damageToChampion;
            private String champion;
            private int kill;
            private int death;
            private int assist;
            private Boolean isBlue;

            @Builder
            public UsersInfoDTO(String puuid, String username, String tag, int damageToChampion, String champion, int kill, int death, int assist, Boolean isBlue) {
                this.puuid = puuid;
                this.username = username;
                this.tag = tag;
                this.damageToChampion = damageToChampion;
                this.champion = champion;
                this.kill = kill;
                this.death = death;
                this.assist = assist;
                this.isBlue = isBlue;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                UsersInfoDTO that = (UsersInfoDTO) o;
                return damageToChampion == that.damageToChampion &&
                        kill == that.kill &&
                        death == that.death &&
                        assist == that.assist &&
                        isBlue == that.isBlue &&
                        Objects.equals(puuid, that.puuid) &&
                        Objects.equals(username, that.username) &&
                        Objects.equals(tag, that.tag) &&
                        Objects.equals(champion, that.champion);
            }

            public static ArrayList<UsersInfoDTO> MakeDTO(ArrayList<RecordUsers> recordUsers) {
                ArrayList<UsersInfoDTO> usersInfoDTOs = new ArrayList<>();
                for (RecordUsers recordUser : recordUsers) {
                    UsersInfoDTO usersInfoDTO = UsersInfoDTO.builder()
                            .puuid(recordUser.getUser().getPuuid())
                            .username(recordUser.getUser().getUsername())
                            .tag(recordUser.getUser().getTag())
                            .damageToChampion(recordUser.getDamageToChampion())
                            .champion(recordUser.getChampion())
                            .kill(recordUser.getKill())
                            .death(recordUser.getDeath())
                            .assist(recordUser.getAssist())
                            .isBlue(recordUser.getIsBlue())
                            .build();
                    usersInfoDTOs.add(usersInfoDTO);
                }
                return usersInfoDTOs;
            }
        }
    }
}
