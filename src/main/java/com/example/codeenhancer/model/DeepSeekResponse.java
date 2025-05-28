package com.example.codeenhancer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty; // Kullanılmasa da, JSON anotasyonları için ekli kalsın
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeepSeekResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private Message message;
        private int index;

        public Message getMessage() { return message; }
        public void setMessage(Message message) { this.message = message; }

        public int getIndex() { return index; }
        public void setIndex(int index) { this.index = index; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private String role;
        private String content;
        private String reasoning; // YENİ ALAN: OpenRouter'dan gelen 'reasoning' verisi için

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        // getContent() metodunu güncelleyerek 'reasoning' alanını öncelikli hale getirdik
        public String getContent() {
            if (content != null && !content.trim().isEmpty()) {
                return content; // Eğer 'content' doluysa onu döndür
            } else if (reasoning != null && !reasoning.trim().isEmpty()) {
                return reasoning; // 'content' boşsa ve 'reasoning' doluysa onu döndür
            }
            return ""; // Her ikisi de boşsa veya null ise boş string döndür
        }
        public void setContent(String content) { this.content = content; }

        // 'reasoning' alanı için Getter ve Setter
        public String getReasoning() { return reasoning; }
        public void setReasoning(String reasoning) { this.reasoning = reasoning; }
    }
}