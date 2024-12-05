package com.example.demo.Utils;

import com.example.demo.Utils.Exceptions.InternalServerError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.demo.Utils.ErrorCode.RIOT_API_ERROR;

@Component
public class RiotAPI {
    private static final Logger logger = LoggerFactory.getLogger(RiotAPI.class);

    @Value("${riot.developer.key}")
    private String apiKey;

    public String getPUUIDFromRiotAPI(String username, String tag) {
        String url = String.format("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s", username, tag);

        // Set up HTTP headers with the API key
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);

        // Wrap headers in an HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                String puuid = rootNode.get("puuid").asText();
                return puuid;
            } catch (Exception e) {
                //TODO 매핑 에러 처리
                logger.error("Error parsing the response body", e);
                throw new InternalServerError(RIOT_API_ERROR);
            }
        } else {
            //TODO 존재하지 않는 데이터입니다 처리 404에러
            logger.error("Error response from Riot API: " + response.getStatusCode());
            throw new InternalServerError(RIOT_API_ERROR);
        }
    }
}