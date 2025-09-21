package com.purpleio.purpleio.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/oembed")
@CrossOrigin(origins = "*")
public class OEmbedController {

    private final WebClient webClient;
    private static final String INSTAGRAM_ACCESS_TOKEN = "your_instagram_access_token_here";

    private static final Map<String, String> ENDPOINTS = Map.of(
            "youtube.com", "https://www.youtube.com/oembed",
            "youtu.be", "https://www.youtube.com/oembed",
            "vimeo.com", "https://vimeo.com/api/oembed.json",
            "twitter.com", "https://publish.twitter.com/oembed",
            "instagram.com", "https://graph.facebook.com/v12.0/instagram_oembed"
    );

    public OEmbedController() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5));

        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @GetMapping
    public Mono<ResponseEntity<? extends Map<String,? extends Object>>> getEmbedData(@RequestParam String url) {
        if (url == null || url.isBlank()) {
            return Mono.just(ResponseEntity.badRequest().body(Map.of("error", "URL 파라미터가 필요합니다.")));
        }

        String endpoint = getEndpoint(url);
        if (endpoint == null) {
            return Mono.just(ResponseEntity.badRequest().body(Map.of("error", "지원하지 않는 URL입니다.")));
        }

        WebClient.RequestHeadersUriSpec<?> requestSpec = webClient.get();

        String apiUrl = endpoint + "?url=" + url;;
        requestSpec.uri(apiUrl);

        return requestSpec
                .headers(headers -> {
                    if (url.contains("youtube.com") || url.contains("youtu.be")) {
                        headers.set(HttpHeaders.REFERER, "https://www.youtube.com/");
                    } else if (url.contains("vimeo.com")) {
                        headers.set(HttpHeaders.REFERER, "https://vimeo.com/");
                    } else if (url.contains("twitter.com")) {
                        headers.set(HttpHeaders.REFERER, "https://twitter.com/");
                    } else if (url.contains("instagram.com")) {
                        headers.set(HttpHeaders.REFERER, "https://www.instagram.com/");
                    }
                })
                .retrieve()
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.just(ResponseEntity.status(responseEntity.getStatusCode())
                                .body(Map.of("error", "외부 API 호출 실패", "code", responseEntity.getStatusCode().toString())));
                    }
                    try {
                        String body = responseEntity.getBody();
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> fullMap = mapper.readValue(body, Map.class);

                        // 트위터의 경우 "html" 필드만 반환
                        if (url.contains("twitter.com")) {
                            String html = (String) fullMap.get("html");
                            return Mono.just(ResponseEntity.ok(Map.of("html", html)));
                        }

                        // 그 외 서비스는 전체 응답 JSON 반환
                        return Mono.just(ResponseEntity.ok(fullMap));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Mono.just(ResponseEntity.status(500).body(Map.of("error", "응답 파싱 실패", "message", e.getMessage())));
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(502).body(Map.of("error", "API 호출 중 예외 발생", "message", e.getMessage()))));
    }

    private String getEndpoint(String url) {
        for (String key : ENDPOINTS.keySet()) {
            if (url.contains(key)) return ENDPOINTS.get(key);
        }
        return null;
    }
}
