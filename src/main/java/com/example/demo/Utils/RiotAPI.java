package com.example.demo.Utils;

import com.example.demo.RecordSearch.dto.RecordsResponse;
import com.example.demo.RecordSearch.entity.RecordUsers;
import com.example.demo.RecordSearch.entity.Records;
import com.example.demo.Utils.Exceptions.InternalServerError;
import com.example.demo.Utils.Exceptions.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
            // 404 에러가 발생하면 NotFoundException을 던짐
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
            // 404 에러가 발생하면 NotFoundException을 던짐
            throw new NotFoundException(RIOT_API_ID_ERROR);
        }
    }

    public RecordsResponse.RecordSearchResponseDTO getRecordsByMatchId(String matchId) {
        String url = "https://asia.api.riotgames.com//lol/match/v5/matches/"+ matchId;

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
                    ArrayList<RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO> blueTeam = new ArrayList<>();
                    ArrayList<RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO> redTeam = new ArrayList<>();

                    //파싱하는부분
                    JsonNode info = rootNode.get("info");
                    String gameType = info.get("queueId").asText();
                    String gameTime = info.get("gameDuration").asText();
                    String endTime = info.get("gameEndTimestamp").asText();
                    Date endTimeDate = convertStringEndTimeToDate(endTime);
                    AtomicInteger bluekill = new AtomicInteger();
                    AtomicInteger redkill = new AtomicInteger();
                    AtomicReference<Boolean> blueWin = new AtomicReference<>();

                    JsonNode participants = info.get("participants");
                    participants.forEach(participant -> {
                        int teamId = participant.get("teamId").asInt();
                        String championName = participant.get("championName").asText();
                        int death = participant.get("deaths").asInt();
                        int kill = participant.get("kills").asInt();
                        int assist = participant.get("assists").asInt();
                        boolean win = participant.get("win").asBoolean();
                        int damage = participant.get("totalDamageDealtToChampions").asInt();

                        RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO usersInfoDTO = new RecordsResponse.RecordSearchResponseDTO.UsersInfoDTO(
                                damage,
                                championName,
                                kill,
                                death,
                                assist
                        );

                        if(teamId == 100){
                            bluekill.addAndGet(kill);
                        }
                        else{
                            redkill.addAndGet(kill);
                        }

                        if(blueWin.get() == null){
                            blueWin.set((teamId == 100 ? win : !win));
                        }
                    });

                    Records records = Records.toEntity(matchId, gameType, gameTime, bluekill.get(), redkill.get(), endTimeDate, blueWin.get());

                    RecordsResponse.RecordSearchResponseDTO recordSearchResponseDTO =
                            new RecordsResponse.RecordSearchResponseDTO(records, blueTeam, redTeam);

                    return recordSearchResponseDTO;
                } catch (Exception e) {
                    logger.error("Error parsing the response body", e);
                    throw new InternalServerError(RIOT_API_ERROR);
                }
            } else {
                throw new InternalServerError(RIOT_API_ERROR);
            }
        } catch (HttpClientErrorException.NotFound e) {
            // 404 에러가 발생하면 NotFoundException을 던짐
            throw new NotFoundException(RIOT_API_ID_ERROR);
        }
    }

    private Date convertStringEndTimeToDate(String endTime){
        long endTimestamp = Long.parseLong(endTime);
        return new Date(endTimestamp);
    }
}