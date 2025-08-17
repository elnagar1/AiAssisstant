package Madfoat.Learning.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    @Value("${ai.provider:demo}")
    private String aiProvider;

    @Value("${openai.api.key:your-api-key-here}")
    private String openaiKey;

    @Value("${huggingface.api.key:your-hf-key-here}")
    private String huggingfaceKey;

    @Value("${ollama.url:http://localhost:11434}")
    private String ollamaUrl;

    @Value("${gemini.api.key:your-gemini-key-here}")
    private String geminiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public AIService() {
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    public String generateTestConditions(String input, String inputType) {
        switch (aiProvider.toLowerCase()) {
            case "openai":
                return generateWithOpenAI(input, inputType);
            case "ollama":
                return generateWithOllama(input, inputType);
            case "huggingface":
                return generateWithHuggingFace(input, inputType);
            case "gemini":
                return generateWithGemini(input, inputType);
            case "demo":
            default:
                return generateDemoTestConditions(input, inputType);
        }
    }

    private String generateWithOpenAI(String input, String inputType) {
        if ("your-api-key-here".equals(openaiKey)) {
            return "OpenAI API key not configured. Please set openai.api.key in application.properties";
        }

        try {
            String prompt = buildPrompt(input, inputType);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "You are an expert software test analyst. Generate comprehensive test conditions based on the provided input."),
                Map.of("role", "user", "content", prompt)
            ));
            requestBody.put("max_tokens", 2000);
            requestBody.put("temperature", 0.7);

            Mono<String> response = webClient.post()
                    .uri("https://api.openai.com/v1/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + openaiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class);

            String responseBody = response.block();
            return extractOpenAIContent(responseBody);

        } catch (Exception e) {
            return "Error with OpenAI: " + e.getMessage();
        }
    }

    private String generateWithOllama(String input, String inputType) {
        try {
            String prompt = buildPrompt(input, inputType);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama2"); // You can change this to other models like "codellama", "mistral"
            requestBody.put("prompt", prompt);
            requestBody.put("stream", false);

            Mono<String> response = webClient.post()
                    .uri(ollamaUrl + "/api/generate")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class);

            String responseBody = response.block();
            return extractOllamaContent(responseBody);

        } catch (Exception e) {
            return "Error with Ollama: " + e.getMessage() + 
                   "\n\nMake sure Ollama is installed and running:\n" +
                   "1. Install Ollama from https://ollama.ai\n" +
                   "2. Run: ollama pull llama2\n" +
                   "3. Start Ollama service";
        }
    }

    private String generateWithHuggingFace(String input, String inputType) {
        if ("your-hf-key-here".equals(huggingfaceKey)) {
            return "Hugging Face API key not configured. Get a free key from https://huggingface.co/settings/tokens";
        }

        try {
            String prompt = buildPrompt(input, inputType);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("inputs", prompt);
            requestBody.put("parameters", Map.of(
                "max_new_tokens", 1000,
                "temperature", 0.7,
                "do_sample", true
            ));

            Mono<String> response = webClient.post()
                    .uri("https://api-inference.huggingface.co/models/google/flan-t5-large")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + huggingfaceKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class);

            String responseBody = response.block();
            return extractHuggingFaceContent(responseBody);

        } catch (Exception e) {
            return "Error with Hugging Face: " + e.getMessage() + 
                   "\n\nGet a free API key from: https://huggingface.co/settings/tokens";
        }
    }

    private String generateWithGemini(String input, String inputType) {
        if ("your-gemini-key-here".equals(geminiKey)) {
            return "Google Gemini API key not configured. Get a free key from https://ai.google.dev/";
        }

        try {
            String prompt = buildPrompt(input, inputType);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", List.of(
                Map.of("parts", List.of(Map.of("text", prompt)))
            ));

            Mono<String> response = webClient.post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header("X-goog-api-key",geminiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class);

            String responseBody = response.block();
            return extractGeminiContent(responseBody);

        } catch (Exception e) {
            return "Error with Gemini: " + e.getMessage() + 
                   "\n\nGet a free API key from: https://ai.google.dev/";
        }
    }

    private String buildPrompt(String input, String inputType) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("You are an expert software test analyst. ");
        
        if ("image".equals(inputType)) {
            prompt.append("Based on the following text extracted from an image or document, ");
        } else {
            prompt.append("Based on the following business requirements or description, ");
        }
        
        prompt.append("generate comprehensive software test cases In excel sheet . ");
        prompt.append("Include functional, non-functional, positive, negative, boundary, and edge case test conditions. ");
        prompt.append("The test cases should have (id , test condition , test case title , steps , expected , actual  ) columns" );
       prompt.append("Format the output as a structured list with clear categories.\n\n");
        prompt.append("Input: ").append(input);
        
        return prompt.toString();
    }

    private String generateDemoTestConditions(String input, String inputType) {
        StringBuilder result = new StringBuilder();
        
        result.append("🔧 DEMO MODE - Sample Test Conditions Generated\n");
        result.append("(Switch to a free AI provider like Ollama or Hugging Face for real AI generation)\n\n");
        
        result.append("📝 INPUT ANALYSIS:\n");
        result.append("Type: ").append(inputType).append("\n");
        result.append("Content Preview: ").append(input.length() > 100 ? input.substring(0, 100) + "..." : input).append("\n\n");
        
        result.append("🧪 FUNCTIONAL TEST CONDITIONS:\n");
        result.append("✓ F001: Verify main functionality works with valid inputs\n");
        result.append("✓ F002: Verify system handles invalid inputs gracefully\n");
        result.append("✓ F003: Verify error messages are displayed for incorrect data\n");
        result.append("✓ F004: Verify success confirmation is shown for valid operations\n");
        result.append("✓ F005: Verify data persistence after successful operations\n\n");
        
        result.append("🔒 SECURITY TEST CONDITIONS:\n");
        result.append("✓ S001: Verify authentication is required for sensitive operations\n");
        result.append("✓ S002: Verify input validation prevents injection attacks\n");
        result.append("✓ S003: Verify session timeout works correctly\n");
        result.append("✓ S004: Verify user permissions are enforced\n\n");
        
        result.append("⚡ PERFORMANCE TEST CONDITIONS:\n");
        result.append("✓ P001: Verify response time is under 3 seconds for normal load\n");
        result.append("✓ P002: Verify system handles 100 concurrent users\n");
        result.append("✓ P003: Verify memory usage stays within acceptable limits\n");
        result.append("✓ P004: Verify system recovers gracefully from high load\n\n");
        
        result.append("📱 USABILITY TEST CONDITIONS:\n");
        result.append("✓ U001: Verify interface is responsive on mobile devices\n");
        result.append("✓ U002: Verify accessibility standards are met\n");
        result.append("✓ U003: Verify user can complete tasks intuitively\n");
        result.append("✓ U004: Verify error messages are user-friendly\n\n");
        
        result.append("🔄 BOUNDARY TEST CONDITIONS:\n");
        result.append("✓ B001: Test with minimum valid input values\n");
        result.append("✓ B002: Test with maximum valid input values\n");
        result.append("✓ B003: Test with values just below minimum threshold\n");
        result.append("✓ B004: Test with values just above maximum threshold\n\n");
        
        result.append("⚠️ NEGATIVE TEST CONDITIONS:\n");
        result.append("✓ N001: Test with null/empty inputs\n");
        result.append("✓ N002: Test with malformed data\n");
        result.append("✓ N003: Test with special characters and Unicode\n");
        result.append("✓ N004: Test system behavior during network failures\n");
        result.append("✓ N005: Test with insufficient system resources\n\n");
        
        result.append("📊 TOTAL: 21 comprehensive test conditions generated\n");
        result.append("💡 TIP: Set ai.provider=ollama for completely free local AI!");
        
        return result.toString();
    }

    // Response extraction methods
    private String extractOpenAIContent(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            return root.get("choices").get(0).get("message").get("content").asText();
        } catch (Exception e) {
            return "Error parsing OpenAI response: " + e.getMessage();
        }
    }

    private String extractOllamaContent(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            return root.get("response").asText();
        } catch (Exception e) {
            return "Error parsing Ollama response: " + e.getMessage();
        }
    }

    private String extractHuggingFaceContent(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            if (root.isArray()) {
                return root.get(0).get("generated_text").asText();
            }
            return root.get("generated_text").asText();
        } catch (Exception e) {
            return "Error parsing Hugging Face response: " + e.getMessage();
        }
    }

    private String extractGeminiContent(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            return root.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
        } catch (Exception e) {
            return "Error parsing Gemini response: " + e.getMessage();
        }
    }
}
