package com.example.app1.vo;

import java.util.Date;

/**
 * Created by 世哲 on 2017/8/8.
 */

public class response {
    private int responseId;
    private int blogid;
    private String username;
    private int floor;
    private String content;
    private Date responseTime;
    private int fabulous=0;
    private int step=0;
    public response(){}
    @Override
    public String toString() {
        return "response{" +
                "responseid=" + responseId +
                ", blogid=" + blogid +
                ", username='" + username + '\'' +
                ", floor=" + floor +
                ", content='" + content + '\'' +
                ", responseTime=" + responseTime +
                ", fabulous=" + fabulous +
                ", step=" + step +
                '}';
    }

    public response(int responseid, int blogid, String username, int floor, String content, Date responseTime, int fabulous, int step) {
        this.responseId = responseid;
        this.blogid = blogid;
        this.username = username;
        this.floor = floor;
        this.content = content;
        this.responseTime = responseTime;
        this.fabulous = fabulous;
        this.step = step;
    }

    public int getResponseid() {
        return responseId;
    }

    public void setResponseid(int responseid) {
        this.responseId = responseid;
    }

    public int getBlogid() {
        return blogid;
    }

    public void setBlogid(int blogid) {
        this.blogid = blogid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public int getFabulous() {
        return fabulous;
    }

    public void setFabulous(int fabulous) {
        this.fabulous = fabulous;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}