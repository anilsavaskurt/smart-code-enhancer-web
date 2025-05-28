package com.example.codeenhancer.model; // model paketi altına taşıdık

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DeepSeekRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    @JsonProperty("max_tokens")
    private int maxTokens;

    public DeepSeekRequest(String model, List<Message> messages, double temperature, int maxTokens) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
    }

    // Getters for Jackson serialization
    public String getModel() { return model; }
    public List<Message> getMessages() { return messages; }
    public double getTemperature() { return temperature; }
    public int getMaxTokens() { return maxTokens; }

    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // Getters for Jackson serialization
        public String getRole() { return role; }
        public String getContent() { return content; }
    }
}