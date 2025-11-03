package com.learning.server.controller;

import com.learning.server.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping("/ping")
    public ResponseEntity<ApiResponse<Map<String, Object>>> ping() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Pong! Server đang hoạt động");
        data.put("timestamp", LocalDateTime.now());
        data.put("status", "healthy");

        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Server ping thành công", data));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        Map<String, String> data = new HashMap<>();
        data.put("status", "UP");
        data.put("application", "Chinese Flashcard API");
        data.put("version", "1.0.0");

        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Health check thành công", data));
    }
}
