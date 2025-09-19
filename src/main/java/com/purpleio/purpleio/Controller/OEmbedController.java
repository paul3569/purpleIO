package com.purpleio.purpleio.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/oembed")
@CrossOrigin(origins = "*")  // 모든 출처 허용
@Tag(name = "oEmbed", description = "oEmbed 데이터 수집")
public class OEmbedController {

    private final RestTemplate restTemplate = new RestTemplate();


    // 지원되는 서비스의 oEmbed 엔드포인트
    private static final Map<String, String> ENDPOINTS = Map.of(
            "youtube.com", "https://www.youtube.com/oembed?format=json&url=",
            "youtu.be", "https://www.youtube.com/oembed?format=json&url=",
            "vimeo.com", "https://vimeo.com/api/oembed.json?url=",
            "twitter.com", "https://publish.twitter.com/oembed?url=",
            "instagram.com", "https://graph.facebook.com/v22.0/instagram_oembed?url="
    );

    @Operation(summary = "oEmbed 데이터 조회 및 썸네일 미리보기 제공")
    @GetMapping
    public ResponseEntity<Map<String, Object>> fetchOEmbed(
            @Parameter(name = "url", description = "미리보기 대상 URL", required = true)
            @RequestParam(name = "url") String url) {
        String endpoint = getOEmbedEndpoint(url);
        if (endpoint == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "지원하지 않는 URL입니다."));
        }

        // Instagram은 별도 처리
        if (isInstagram(url)) {
            return handleInstagramOembed(url);
        }

        try {

            String encodeUrl = URLEncoder.encode(url, StandardCharsets.UTF_8);
            System.out.println("encodeUrl : " + encodeUrl);
            System.out.println("fullUrl : " + endpoint + encodeUrl);
//            String response = restTemplate.getForObject(endpoint + URLEncoder.encode(url, StandardCharsets.UTF_8), String.class);
            String response = restTemplate.getForObject(endpoint + encodeUrl, String.class);
            System.out.println("response : " + response);
            // JSON 응답을 Map으로 반환
            Map<String, Object> oembedData = new ObjectMapper().readValue(response, Map.class);
            return ResponseEntity.ok(oembedData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of("error", "oEmbed 요청 실패", "message", e.getMessage()));
        }
    }

    private boolean isInstagram(String url) {
        return url.contains("instagram.com");
    }

    // Instagram 별도 처리: 액세스 토큰 필수, 썸네일 필드 지원 중단
    private ResponseEntity<Map<String, Object>> handleInstagramOembed(String url) {
        String accessToken = System.getenv("IG_OEMBED_TOKEN");
        if (accessToken == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Instagram oEmbed를 사용하려면 액세스 토큰이 필요합니다."));
        }
        String endpoint = ENDPOINTS.get("instagram.com") + URLEncoder.encode(url, StandardCharsets.UTF_8)
                + "&access_token=" + accessToken;
        try {
            String response = restTemplate.getForObject(endpoint, String.class);
            Map<String, Object> oembedData = new ObjectMapper().readValue(response, Map.class);
            if (!oembedData.containsKey("thumbnail_url")) {
                oembedData.put("thumbnail_url", "썸네일 필드 제공 불가(API 정책 변경)");
            }
            return ResponseEntity.ok(oembedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Instagram oEmbed 요청 실패", "message", e.getMessage()));
        }
    }

    private String getOEmbedEndpoint(String url) {
        for (String key : ENDPOINTS.keySet()) {
            if (url.contains(key)) return ENDPOINTS.get(key);
        }
        return null;
    }
}

