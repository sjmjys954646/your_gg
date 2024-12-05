package com.example.demo.RecordSearch.entity;

import com.example.demo.User.entity.Users;
import com.example.demo.Utils.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = FALSE")
@SQLDelete(sql = "UPDATE recordusers SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "recordusers")
public class RecordUsers extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Records record;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    //딜량
    private int damageToChampion;
    private String champion;
    private int kill;
    private int death;
    private int assist;

    @Builder
    public RecordUsers(Records record, Users user, int damageToChampion, String champion, int kill, int death, int assist) {
        this.record = record;
        this.user = user;
        this.damageToChampion = damageToChampion;
        this.champion = champion;
        this.kill = kill;
        this.death = death;
        this.assist = assist;
    }
}