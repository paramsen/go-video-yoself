package com.amsen.par.govideoyoself.model;

/**
 * @author Pär Amsen 2016
 */
public class VideoStatus {
    private int id;
    private String displayName;
    private String unicodeIcon;
    private boolean completed;

    public VideoStatus(int id, String displayName, String unicodeIcon, boolean completed) {
        this.id = id;
        this.displayName = displayName;
        this.unicodeIcon = unicodeIcon;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getUnicodeIcon() {
        return unicodeIcon;
    }

    public void setUnicodeIcon(String unicodeIcon) {
        this.unicodeIcon = unicodeIcon;
    }
}
