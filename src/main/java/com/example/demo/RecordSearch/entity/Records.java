package com.example.demo.RecordSearch.entity;

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

    @Builder
    public Records(String matchId, String gameType, String gameTime, int blueKill, int redKill, Date endTime, boolean bluewin) {
        this.matchId = matchId;
        this.gameType = gameType;
        this.gameTime = gameTime;
        this.blueKill = blueKill;
        this.redKill = redKill;
        this.endTime = endTime;
        this.bluewin = bluewin;
    }

    public static Records toEntity(String matchId, String gameType, String gameTime, int blueKill, int redKill, Date endTime, boolean bluewin) {
        return Records.builder()
                .matchId(matchId)
                .gameType(gameType)
                .gameTime(gameTime)
                .blueKill(blueKill)
                .redKill(redKill)
                .endTime(endTime)
                .bluewin(bluewin)
                .build();
    }
}