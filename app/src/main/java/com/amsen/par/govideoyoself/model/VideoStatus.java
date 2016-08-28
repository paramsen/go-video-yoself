package com.amsen.par.govideoyoself.model;

/**
 * @author Pär Amsen 2016
 */
public class VideoStatus {
    private int id;
    private String actionDisplayString;
    private String unicodeIcon;
    private boolean completed;

    public VideoStatus(int id, String actionDisplayString, String unicodeIcon, boolean completed) {
        this.id = id;
        this.actionDisplayString = actionDisplayString;
        this.unicodeIcon = unicodeIcon;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionDisplayString() {
        return actionDisplayString;
    }

    public void setActionDisplayString(String actionDisplayString) {
        this.actionDisplayString = actionDisplayString;
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
