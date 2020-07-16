package com.project.smartdoor.ui.login;

import android.os.Parcelable;

import java.io.Serializable;

public class Data implements Serializable {

    private String log_id;
    private String log_time;

    public Data(String log_id, String log_time){
        this.log_id = log_id;
        this.log_time =log_time;
    }

    public String getLog_id(){
        return log_id;
    }

    public  String getLog_time(){
        return log_time;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public void setLog_time(String log_time) {
        this.log_time = log_time;
    }
}