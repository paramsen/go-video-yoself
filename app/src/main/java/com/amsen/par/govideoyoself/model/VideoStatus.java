package com.amsen.par.govideoyoself.model;

/**
 * @author Pär Amsen 2016
 */
public class VideoStatus {
    private int id;
    private String displayName;
    private boolean completed;

    public VideoStatus(int id, String displayName, boolean completed) {
        this.id = id;
        this.displayName = displayName;
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
}
