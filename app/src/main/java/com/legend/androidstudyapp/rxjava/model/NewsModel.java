package com.legend.androidstudyapp.rxjava.model;

public class NewsModel {
    private long id;
    private String newId;
    private String headImg;
    private long publishedTime;
    private String channel;
    private String detailUrl;
    private String title;
    private long playTime;

    //布局前面是否有checkbox
    private boolean isCheckBoxVisible = false;

    //是否选中
    private boolean isChecked = false;

    public NewsModel(String newId,String headImg, long publishedTime, String channel, String sourceLink, String title) {
        this.newId = newId;
        this.headImg = headImg;
        this.publishedTime = publishedTime;
        this.channel = channel;
        this.detailUrl = sourceLink;
        this.title = title;
    }
    public NewsModel(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getCover() {
        return headImg;
    }

    public void setCover(String headImg) {
        this.headImg = headImg;
    }

    public long getReleaseTime() {
        return publishedTime;
    }

    public void setReleaseTime(long publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getSource() {
        return channel;
    }

    public void setSource(String channel) {
        this.channel = channel;
    }

    public String getLink() {
        return detailUrl;
    }

    public void setLink(String sourceLink) {
        this.detailUrl = sourceLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public boolean isCheckBoxVisible() {
        return isCheckBoxVisible;
    }

    public void setCheckBoxVisible(boolean select) {
        isCheckBoxVisible = select;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
