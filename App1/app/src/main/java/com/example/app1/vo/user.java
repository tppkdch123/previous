package com.example.app1.vo;

/**
 * Created by 世哲 on 2017/8/8.
 */

public class user {
    private String username;
    private String password;
    public user(){}
    public user(String username, String password, String name, String introduction, int coins, boolean isSuperManager) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.introduction = introduction;
        this.coins = coins;
        this.isSuperManager = isSuperManager;
    }

    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", coins=" + coins +
                ", isSuperManager=" + isSuperManager +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setSuperManager(boolean superManager) {
        isSuperManager = superManager;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isSuperManager() {
        return isSuperManager;
    }

    private String name;
    private String introduction;
    private int coins;
    private boolean isSuperManager;
}
