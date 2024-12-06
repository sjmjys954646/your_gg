package com.example.demo.RecordSearch.entity;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.User.entity.Users;
import com.example.demo.Utils.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    private int damageToChampion;

    @Pattern(regexp = "^[a-zA-Z]{1,50}$", message = "영어 알파벳만 입력 가능하며 50자 이내여야합니다")
    private String champion;

    private int kill;
    private int death;
    private int assist;
    private Boolean isBlue;

    @Builder
    public RecordUsers(Records record, Users user, RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO usersInfoDTO) {
        this.record = record;
        this.user = user;
        this.damageToChampion = usersInfoDTO.getDamageToChampion();
        this.champion = usersInfoDTO.getChampion();
        this.kill = usersInfoDTO.getKill();
        this.death = usersInfoDTO.getDeath();
        this.assist = usersInfoDTO.getAssist();
        this.isBlue = usersInfoDTO.getIsBlue();
    }
}
