package com.example.demo.Utils;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.Utils.Exceptions.InternalServerError;
import com.example.demo.Utils.Exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.example.demo.Utils.ErrorCode.*;

@Component
public class RiotAPI {
    private static final Logger logger = LoggerFactory.getLogger(RiotAPI.class);

    @Value("${riot.developer.key}")
    private String apiKey;

    public String getPUUIDFromRiotAPI(String username, String tag) {
        String url = String.format("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s", username, tag);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.getBody());
                    String puuid = rootNode.get("puuid").asText();
                    return puuid;
                } catch (Exception e) {
                    logger.error("Error parsing the response body", e);
                    throw new InternalServerError(RIOT_API_ERROR);
                }
            } else {
                throw new InternalServerError(RIOT_API_ERROR);
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException(RIOT_API_ID_ERROR);
        }
    }

    public String getMatchIdByQueueFromRiotAPI(String puuid, String queue) {
        String url = "https://asia.api.riotgames.com//lol/match/v5/matches/by-puuid/"+ puuid + "/ids?queue=" + queue + "&start=0&count=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.getBody());
                    if (rootNode.isArray() && rootNode.size() > 0) {
                        String matchId = rootNode.get(0).asText();
                        return matchId;
                    }
                    else{
                        return null;
                    }

                } catch (Exception e) {
                    logger.error("Error parsing the response body", e);
                    throw new InternalServerError(RIOT_API_ERROR);
                }
            } else {
                throw new InternalServerError(RIOT_API_ERROR);
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException(RIOT_API_ID_ERROR);
        }
    }

    public RecordsResponse.RecordSearchResponseDTO getRecordsByMatchId(String matchId) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://asia.api.riotgames.com")
                .defaultHeader("X-Riot-Token", apiKey) // 기본 헤더 설정
                .build();

        try {
            String responseBody = webClient.get()
                    .uri("/lol/match/v5/matches/{matchId}", matchId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            JsonNode info = rootNode.get("info");
            ArrayList<RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO> blueTeam = new ArrayList<>();
            ArrayList<RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO> redTeam = new ArrayList<>();

            String gameType = info.path("queueId").asText("");
            String gameTime = info.path("gameDuration").asText("");
            String endTime = info.path("gameEndTimestamp").asText("");
            Date endTimeDate = convertStringEndTimeToDate(endTime);

            AtomicInteger bluekill = new AtomicInteger();
            AtomicInteger redkill = new AtomicInteger();
            AtomicReference<Boolean> blueWin = new AtomicReference<>(null);

            // 참가자 정보 파싱
            JsonNode participants = info.path("participants");
            participants.forEach(participant -> {
                int teamId = participant.path("teamId").asInt();
                String puuid = participant.path("puuid").asText();
                String username = participant.path("riotIdGameName").asText();
                String usertag = participant.path("riotIdTagline").asText();
                String championName = participant.path("championName").asText("");
                int death = participant.path("deaths").asInt(0);
                int kill = participant.path("kills").asInt(0);
                int assist = participant.path("assists").asInt(0);
                boolean win = participant.path("win").asBoolean(false);
                int damage = participant.path("totalDamageDealtToChampions").asInt(0);

                RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO usersInfoDTO =
                        new RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO(
                                puuid, username, usertag, damage, championName, kill, death, assist, teamId == 100
                        );

                if (teamId == 100) {
                    bluekill.addAndGet(kill);
                    blueTeam.add(usersInfoDTO);
                } else if (teamId == 200) {
                    redkill.addAndGet(kill);
                    redTeam.add(usersInfoDTO);
                }

                if (blueWin.get() == null) {
                    blueWin.set((teamId == 100) ? win : !win);
                }
            });

            // 결과 객체 생성
            RecordsResponse.RecordSearchResponseDTO.RecordInfoDTO records =
                    new RecordsResponse.RecordSearchResponseDTO.RecordInfoDTO(
                            matchId, gameType, gameTime, bluekill.get(), redkill.get(), endTimeDate, blueWin.get()
                    );

            return new RecordsResponse.RecordSearchResponseDTO(records, blueTeam, redTeam);

        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Match ID not found in Riot API: " + matchId, e);
            throw new NotFoundException(RIOT_API_ID_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error while processing Riot API response", e);
            throw new InternalServerError(RIOT_API_ERROR);
        }
    }

    private Date convertStringEndTimeToDate(String endTime){
        long endTimestamp = Long.parseLong(endTime);
        return new Date(endTimestamp);
    }
}