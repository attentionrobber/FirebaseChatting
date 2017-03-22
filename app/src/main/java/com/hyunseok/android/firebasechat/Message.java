package com.hyunseok.android.firebasechat;

/**
 * Created by Administrator on 2017-03-13.
 */

public class Message {
    String key;
    String userid;
    String username;
    String msg;
    String ndate;

    public void setKey(String key) {
        this.key = key;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {

        return msg;
    }

    public String getKey() {

        return key;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return msg;
    }

    public String getNdate() {
        return ndate;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }
}
