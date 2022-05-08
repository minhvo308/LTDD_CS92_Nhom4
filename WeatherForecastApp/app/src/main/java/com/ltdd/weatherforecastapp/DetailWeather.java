package com.ltdd.weatherforecastapp;

public class DetailWeather {
    private String name;
    private String content;
    private String titleSubContent;
    private String subContent;

    public DetailWeather(String name, String content, String titleSubContent, String subContent) {
        this.name = name;
        this.content = content;
        this.titleSubContent = titleSubContent;
        this.subContent = subContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public String getTitleSubContent() {
        return titleSubContent;
    }

    public void setTitleSubContent(String titleSubContent) {
        this.titleSubContent = titleSubContent;
    }
}
