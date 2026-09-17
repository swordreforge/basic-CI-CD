package com.example.exp4.controller;

import com.example.exp4.ai.AiSecurityGuard;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiSecurityController {

    public record CheckRequest(String input) {
    }

    public record CheckResult(String sanitized, boolean suspicious, boolean containsSensitive, String masked) {
    }

    @PostMapping("/check")
    public CheckResult check(@RequestBody CheckRequest request) {
        String sanitized = AiSecurityGuard.sanitize(request.input());
        return new CheckResult(
                sanitized,
                AiSecurityGuard.isPromptInjection(sanitized),
                AiSecurityGuard.containsSensitive(sanitized),
                AiSecurityGuard.mask(sanitized)
        );
    }
}
