package com.example.demo.User.entity;

import com.example.demo.Utils.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = FALSE")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "users")
public class Users extends BaseTime {
     /*
    https://support-leagueoflegends.riotgames.com/hc/ko/articles/360041788533-Riot-ID-FAQ
    라이엇 이름 생성 규칙
    - 게임이름 3~16의 영숫자
    - 태그라인 # + 3~5 영숫자
    - 한국어는 가능해야되니 특수문자만제외
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>_+\\-=\\[\\]';/]{1,20}$", message = "특수문자는 사용할 수 없습니다. 20자 이내여야합니다.")
    @Column(nullable = false)
    private String username;

    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>_+\\-=\\[\\]';/]{1,20}", message = "특수문자는 사용할 수 없습니다. 20자 이내여야합니다.")
    @Column(nullable = false)
    private String tag;

    @Size(max = 50, message = "길이는 최대 50자여야 합니다.")
    private String puuid;

    @Builder
    public Users(String username, String tag, String puuid) {
        this.username = username;
        this.tag = tag;
        this.puuid = puuid;
    }

    public static Users toEntity(String username, String tag, String puuid){
        return Users.builder()
                .username(username)
                .tag(tag)
                .puuid(puuid)
                .build();
    }
}
