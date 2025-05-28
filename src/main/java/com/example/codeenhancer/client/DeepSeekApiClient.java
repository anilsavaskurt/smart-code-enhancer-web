package com.example.codeenhancer.client;

import com.example.codeenhancer.model.DeepSeekRequest;
import com.example.codeenhancer.model.DeepSeekResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeepSeekApiClient {

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @Value("${deepseek.api.key}")
    private String apiKey;

    // OpenRouter için isteğe bağlı başlıklar
    @Value("${openrouter.http-referer:#{null}}") // Eğer yoksa null gelir
    private String httpReferer;

    @Value("${openrouter.x-title:#{null}}") // Eğer yoksa null gelir
    private String xTitle;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public DeepSeekApiClient(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public String getCodeReview(String prompt, String model, double temperature, int maxTokens) throws IOException {
        DeepSeekRequest.Message userMessage = new DeepSeekRequest.Message("user", prompt);
        DeepSeekRequest requestBody = new DeepSeekRequest(
                model,
                java.util.Collections.singletonList(userMessage),
                temperature,
                maxTokens
        );

        String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

        RequestBody body = RequestBody.create(jsonRequestBody, JSON);

        Request.Builder requestBuilder = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey);

        // OpenRouter'a özel isteğe bağlı başlıkları ekle
        if (httpReferer != null && !httpReferer.isEmpty()) {
            requestBuilder.addHeader("HTTP-Referer", httpReferer);
        }
        if (xTitle != null && !xTitle.isEmpty()) {
            requestBuilder.addHeader("X-Title", xTitle);
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error body";
                throw new IOException("API'den beklenmeyen yanıt: " + response.code() + " - " + errorBody);
            }

            String responseBody = response.body().string();
            DeepSeekResponse deepSeekResponse = objectMapper.readValue(responseBody, DeepSeekResponse.class);

            if (deepSeekResponse != null && deepSeekResponse.getChoices() != null && !deepSeekResponse.getChoices().isEmpty()) {
                return deepSeekResponse.getChoices().get(0).getMessage().getContent();
            } else {
                return "AI'dan öneri alınamadı veya yanıt formatı beklenenden farklı.";
            }
        }
    }
}