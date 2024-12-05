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
    private Records records;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    //딜량
    private int deal;
    private String champion;

    @Builder
    public RecordUsers(Records records, Users users, int deal, String champion) {
        this.records = records;
        this.users = users;
        this.deal = deal;
        this.champion = champion;
    }
}
