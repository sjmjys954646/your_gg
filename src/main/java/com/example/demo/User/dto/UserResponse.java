package com.example.demo.User.dto;

import lombok.Getter;

public class UserResponse {

    /*
    https://support-leagueoflegends.riotgames.com/hc/ko/articles/360041788533-Riot-ID-FAQ
    라이엇 이름 생성 규칙
    - 게임이름 3~16의 영숫자
    - 태그라인 # + 3~5 영숫자
    */
    @Getter
    public static class UserResponseDTO{
        String id;
        String tag;
        String idAndTag;
        String puuid;
    }
}
