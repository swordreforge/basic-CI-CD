package com.example.exp4.ai;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

// AI-assisted: 提示注入与敏感信息防护示例，拦截规则需按业务场景人工复核。
public final class AiSecurityGuard {

    private static final List<String> INJECTION_PATTERNS = List.of(
            "ignore previous instructions",
            "ignore all previous",
            "disregard prior",
            "reveal your instructions",
            "system prompt",
            "you are now",
            "jailbreak",
            "忽略之前的指令",
            "忘记以上内容",
            "泄露系统提示词"
    );

    private static final Pattern PHONE = Pattern.compile("1[3-9]\\d{9}");
    private static final Pattern ID_CARD = Pattern.compile("\\b\\d{17}[0-9Xx]\\b");
    private static final Pattern EMAIL = Pattern.compile("[\\w.+-]+@[\\w-]+\\.[\\w.]+");

    private AiSecurityGuard() {
    }

    public static String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("[\\r\\n]+", " ").trim();
    }

    public static boolean isPromptInjection(String input) {
        if (input == null || input.isBlank()) {
            return false;
        }
        String lower = input.toLowerCase(Locale.ROOT);
        return INJECTION_PATTERNS.stream().anyMatch(lower::contains);
    }

    public static boolean containsSensitive(String input) {
        if (input == null || input.isBlank()) {
            return false;
        }
        return PHONE.matcher(input).find()
                || ID_CARD.matcher(input).find()
                || EMAIL.matcher(input).find();
    }

    public static String mask(String input) {
        if (input == null) {
            return "";
        }
        return EMAIL.matcher(
                ID_CARD.matcher(
                        PHONE.matcher(input).replaceAll("***")
                ).replaceAll("***")
        ).replaceAll("***");
    }
}
