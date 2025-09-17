package Madfoat.Learning.controller;

import Madfoat.Learning.dto.BugReport;
import Madfoat.Learning.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/bug")
public class BugController {

    private final AIService aiService;

    @Autowired
    public BugController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BugReport generateBug(@RequestParam("text") String text,
                                 @RequestParam(value = "image", required = false) MultipartFile image) {
        byte[] imageBytes = null;
        String fileName = null;
        try {
            if (image != null && !image.isEmpty()) {
                imageBytes = image.getBytes();
                fileName = image.getOriginalFilename();
            }
        } catch (Exception ignored) {}
        return aiService.generateBugReport(text, imageBytes, fileName);
    }
}

