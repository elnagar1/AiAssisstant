package Madfoat.Learning.dto;

import java.util.List;

public class BugReport {
    private String title;
    private String description;
    private List<String> steps;
    private String severity;
    private String bugType;

    public BugReport() {}

    public BugReport(String title, String description, List<String> steps, String severity, String bugType) {
        this.title = title;
        this.description = description;
        this.steps = steps;
        this.severity = severity;
        this.bugType = bugType;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getBugType() { return bugType; }
    public void setBugType(String bugType) { this.bugType = bugType; }
}

