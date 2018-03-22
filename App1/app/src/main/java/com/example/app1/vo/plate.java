package com.example.app1.vo;

/**
 * Created by 世哲 on 2017/8/5.
 */


public class plate {
    private int plateid;
    private String username;
    private String platename;
    private String plateType;
    private boolean isClose;
    public plate(){}
    public plate(int palteid, String username, String platename, String plateType, boolean isClose) {
        this.plateid = palteid;
        this.username = username;
        this.platename = platename;
        this.plateType = plateType;
        this.isClose = isClose;
    }
    @Override
    public String toString() {
        return "plate{" +
                "palteid=" + plateid +
                ", username='" + username + '\'' +
                ", platename='" + platename + '\'' +
                ", plateType='" + plateType + '\'' +
                ", isClose=" + isClose +
                '}';
    }

    public int getPalteid() {
        return plateid;
    }

    public void setPlateid(int palteid) {
        this.plateid = palteid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlatename() {
        return platename;
    }

    public void setPlatename(String platename) {
        this.platename = platename;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }
}