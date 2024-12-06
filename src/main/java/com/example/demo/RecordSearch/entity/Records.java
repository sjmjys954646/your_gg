package com.example.demo.RecordSearch.entity;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.User.entity.Users;
import com.example.demo.Utils.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = FALSE")
@SQLDelete(sql = "UPDATE records SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "records")
public class Records extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matchId;
    //ENUM 처리
    private String gameType;
    private String gameTime;
    private int blueKill;
    private int redKill;
    private Date endTime;
    private boolean bluewin;
    private int blueGold;
    private int redGold;
    private int maxDamage;

    @Builder
    public Records(String matchId, String gameType, String gameTime, int blueKill, int redKill, Date endTime, boolean bluewin, int blueGold, int redGold, int maxDamage) {
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

    public static Records toEntity(RecordsResponse.RecordSearchResponseDTO.RecordInfoDTO recordInfoDTO) {
        return Records.builder()
                .matchId(recordInfoDTO.getMatchId())
                .gameType(recordInfoDTO.getGameType())
                .gameTime(recordInfoDTO.getGameTime())
                .blueKill(recordInfoDTO.getBlueKill())
                .redKill(recordInfoDTO.getRedKill())
                .endTime(recordInfoDTO.getEndTime())
                .bluewin(recordInfoDTO.getBluewin())
                .blueGold(recordInfoDTO.getBlueGold())
                .redGold(recordInfoDTO.getRedGold())
                .build();
    }
}
