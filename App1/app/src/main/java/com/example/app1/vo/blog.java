package com.example.app1.vo;

/**
 * Created by 世哲 on 2017/8/6.
 */

import java.util.Date;

public class blog {
    private String title;
    private int plateid;
    private String username;
    private String describes;
    private int blogid;
    private Date createtime;
    private int floornumber;
    private boolean isClose;
    private boolean isTop;
    private String blogtype;

    public blog() {
    }

    public blog(String title, int plateid, String username, String describes, int blogid, Date createtime, int floornumber, boolean isClose, boolean isTop, String blogtype) {
        this.title = title;
        this.plateid = plateid;
        this.username = username;
        this.describes = describes;
        this.blogid = blogid;
        this.createtime = createtime;
        this.floornumber = floornumber;
        this.isClose = isClose;
        this.isTop = isTop;
        this.blogtype = blogtype;
    }

    @Override
    public String toString() {
        return "blog{" +
                "title='" + title + '\'' +
                ", plateid=" + plateid +
                ", username='" + username + '\'' +
                ", describes='" + describes + '\'' +
                ", blogid=" + blogid +
                ", createtime=" + createtime +
                ", floornumber=" + floornumber +
                ", isClose=" + isClose +
                ", isTop=" + isTop +
                ", blogtype='" + blogtype + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public int getBlogid() {
        return blogid;
    }

    public void setBlogid(int blogid) {
        this.blogid = blogid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getFloornumber() {
        return floornumber;
    }

    public void setFloornumber(int floornumber) {
        this.floornumber = floornumber;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public String getBlogtype() {
        return blogtype;
    }

    public void setBlogtype(String blogtype) {
        this.blogtype = blogtype;
    }
}