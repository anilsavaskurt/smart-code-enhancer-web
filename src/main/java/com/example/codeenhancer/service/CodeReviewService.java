package com.example.codeenhancer.service;

import com.example.codeenhancer.client.DeepSeekApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CodeReviewService {

    private final DeepSeekApiClient deepSeekApiClient;

    @Value("${deepseek.api.model}")
    private String deepSeekModel;

    @Value("${deepseek.api.temperature}")
    private double deepSeekTemperature;

    @Value("${deepseek.api.max-tokens}")
    private int deepSeekMaxTokens;

    public CodeReviewService(DeepSeekApiClient deepSeekApiClient) {
        this.deepSeekApiClient = deepSeekApiClient;
    }

    public String reviewCode(String javaCode) throws IOException {
        String prompt = "Review the following Java code for best practices, readability, potential performance issues, and security vulnerabilities. Provide clear, actionable suggestions in bullet points. If the code is well-written, state that. Focus on constructive criticism.\n\nJava Code:\n```java\n" + javaCode + "\n```\n\nCode Review Suggestions:";

        return deepSeekApiClient.getCodeReview(prompt, deepSeekModel, deepSeekTemperature, deepSeekMaxTokens);
    }
}