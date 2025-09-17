package Madfoat.Learning.dto;

import java.util.List;

public class BugReport {
    private String title;
    private String description;
    private List<String> steps;
    private String severity;
    private String bugType;
    private String environment;
    private String expected;
    private String actual;
    private String reporter;
    private java.util.List<String> attachments;
    private String module;
    private Boolean reproducible;
    private String dateReported;
    private String version;
    private String priority;
    private String status;

    public BugReport() {}

    public BugReport(String title, String description, List<String> steps, String severity, String bugType) {
        this(title, description, steps, severity, bugType, null, null, null, null, null, null, null, null, null, null, null);
    }

    public BugReport(String title, String description, List<String> steps, String severity, String bugType,
                     String environment, String expected, String actual, String reporter, List<String> attachments,
                     String module, Boolean reproducible, String dateReported, String version, String priority, String status) {
        this.title = title;
        this.description = description;
        this.steps = steps;
        this.severity = severity;
        this.bugType = bugType;
        this.environment = environment;
        this.expected = expected;
        this.actual = actual;
        this.reporter = reporter;
        this.attachments = attachments;
        this.module = module;
        this.reproducible = reproducible;
        this.dateReported = dateReported;
        this.version = version;
        this.priority = priority;
        this.status = status;
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

    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }

    public String getExpected() { return expected; }
    public void setExpected(String expected) { this.expected = expected; }

    public String getActual() { return actual; }
    public void setActual(String actual) { this.actual = actual; }

    public String getReporter() { return reporter; }
    public void setReporter(String reporter) { this.reporter = reporter; }

    public java.util.List<String> getAttachments() { return attachments; }
    public void setAttachments(java.util.List<String> attachments) { this.attachments = attachments; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public Boolean getReproducible() { return reproducible; }
    public void setReproducible(Boolean reproducible) { this.reproducible = reproducible; }

    public String getDateReported() { return dateReported; }
    public void setDateReported(String dateReported) { this.dateReported = dateReported; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

