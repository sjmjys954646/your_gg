package com.example.demo.User.entity;

import com.example.demo.Utils.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@SQLRestriction("is_deleted = FALSE")
@SQLDelete(sql = "UPDATE cargo SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "user")
public class User extends BaseTime {
     /*
    https://support-leagueoflegends.riotgames.com/hc/ko/articles/360041788533-Riot-ID-FAQ
    라이엇 이름 생성 규칙
    - 게임이름 3~16의 영숫자
    - 태그라인 # + 3~5 영숫자
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,16}$", message = "영문 대소문자와 숫자만 입력 가능합니다.")
    @Column(nullable = false)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,5}$", message = "영문 대소문자와 숫자만 입력 가능합니다.")
    @Column(nullable = false)
    private String tag;

    private String puuid;

    @Builder
    public User(String username, String tag, String puuid) {
        this.username = username;
        this.tag = tag;
        this.puuid = puuid;
    }

    public static User toEntity(String username, String tag, String puuid){
        return User.builder()
                .username(username)
                .tag(tag)
                .puuid(puuid)
                .build();
    }
}
