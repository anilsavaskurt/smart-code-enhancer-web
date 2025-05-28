package com.example.codeenhancer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(30)) // Bağlantı zaman aşımı
                .readTimeout(Duration.ofSeconds(60))    // Okuma zaman aşımı
                .writeTimeout(Duration.ofSeconds(60))   // Yazma zaman aşımı
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}