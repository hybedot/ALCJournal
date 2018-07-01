package com.androidrefugee.journal.Model;

public class UserModel {
    private String userId;
    private Long timeStamp;
    private String title;
    private String bodyMessage;
    private String moods;



    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoods() {
        return moods;
    }

    public void setMoods(String moods) {
        this.moods = moods;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
