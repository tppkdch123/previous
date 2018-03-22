package com.example.app1.vo;

import java.util.Date;

/**
 * Created by 世哲 on 2017/8/8.
 */

public class follow {
    private int plateid;
    private String username;
    private boolean isManager;
    private int prestige;
    private Date followtime;
    public follow(){}
    @Override
    public String toString() {
        return "follow{" +
                "plateid=" + plateid +
                ", username='" + username + '\'' +
                ", isManager=" + isManager +
                ", prestige=" + prestige +
                ", followtime=" + followtime +
                '}';
    }

    public follow(int plateid, String username, boolean isManager, int prestige, Date followtime) {
        this.plateid = plateid;
        this.username = username;
        this.isManager = isManager;
        this.prestige = prestige;
        this.followtime = followtime;
    }

    public int getPlateid() {
        return plateid;
    }

    public void setPlateid(int plateid) {
        this.plateid = plateid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    public Date getFollowtime() {
        return followtime;
    }

    public void setFollowtime(Date followtime) {
        this.followtime = followtime;
    }
}
