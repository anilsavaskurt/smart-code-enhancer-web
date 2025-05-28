package com.example.codeenhancer.controller;

import com.example.codeenhancer.model.CodeReviewRequest;
import com.example.codeenhancer.model.CodeReviewResponse;
import com.example.codeenhancer.service.CodeReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody; // BURASI DEĞİŞTİ!
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CodeReviewController {

    private final CodeReviewService codeReviewService;

    public CodeReviewController(CodeReviewService codeReviewService) {
        this.codeReviewService = codeReviewService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("codeReviewRequest", new CodeReviewRequest());
        model.addAttribute("codeReviewResponse", new CodeReviewResponse("", ""));
        return "index";
    }

    @PostMapping("/review")
    @ResponseBody
    public CodeReviewResponse reviewCode(@RequestBody CodeReviewRequest request) { // BURASI DEĞİŞTİ!
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            return new CodeReviewResponse("", "Lütfen review edilecek Java kodunu girin.");
        }

        try {
            String suggestions = codeReviewService.reviewCode(request.getCode());
            return new CodeReviewResponse(suggestions, null);
        } catch (Exception e) {
            System.err.println("Kod review sırasında hata oluştu: " + e.getMessage());
            e.printStackTrace();
            return new CodeReviewResponse(null, "Kod review sırasında bir hata oluştu: " + e.getMessage());
        }
    }
}