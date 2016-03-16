package com.frank.sample.bean;

public class TimelineBean {

    public static final String TYPE_GAME = "game";
    public static final String TYPE_VIDEO = "video";

    private String eventType;
    private GameBean gameBean;
    private VideoBean videoBean;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public GameBean getGameBean() {
        return gameBean;
    }

    public void setGameBean(GameBean gameBean) {
        this.gameBean = gameBean;
    }

    public VideoBean getVideoBean() {
        return videoBean;
    }

    public void setVideoBean(VideoBean videoBean) {
        this.videoBean = videoBean;
    }
}
