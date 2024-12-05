package com.example.demo.RecordSearch.entity;

import com.example.demo.User.entity.Users;
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
@SQLDelete(sql = "UPDATE userrecentrecords SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "userrecentrecords")
public class UserRecentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Records record;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @Builder
    public UserRecentRecord(Records record, Users user) {
        this.record = record;
        this.user = user;
    }
}
