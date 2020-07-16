package com.project.smartdoor.ui.login;

import java.io.Serializable;

public class Noti implements Serializable {
    private int noti_id;
    private  String noti_title;
    private  String noti_desc;
    private  String noti_content;
    private  String noti_admin;
    private  String noti_time;


    public int getNoti_id() {
        return noti_id;
    }

    public void setNoti_id(int noti_id) {
        this.noti_id = noti_id;
    }

    public String getNoti_title() {
        return noti_title;
    }

    public void setNoti_title(String noti_title) {
        this.noti_title = noti_title;
    }

    public String getNoti_content() {
        return noti_content;
    }

    public void setNoti_content(String noti_content) {
        this.noti_content = noti_content;
    }

    public String getNoti_admin() {
        return noti_admin;
    }

    public void setNoti_admin(String noti_admin) {
        this.noti_admin = noti_admin;
    }

    public String getNoti_time() {
        return noti_time;
    }

    public void setNoti_time(String noti_time) {
        this.noti_time = noti_time;
    }

    public String getNoti_desc() {
        return noti_desc;
    }

    public void setNoti_desc(String noti_desc) {
        this.noti_desc = noti_desc;
    }
}
