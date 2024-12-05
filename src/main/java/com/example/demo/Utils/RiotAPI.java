package com.example.demo.Utils;

import com.example.demo.Utils.Exceptions.InternalServerError;
import com.example.demo.Utils.Exceptions.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
                    throw new InternalServerError(RIOD_API_ERROR);
                }
            } else {
                throw new InternalServerError(RIOD_API_ERROR);
            }
        } catch (HttpClientErrorException.NotFound e) {
            // 404 에러가 발생하면 NotFoundException을 던짐
            throw new NotFoundException(RIOT_API_ID_ERROR);
        }
    }

}