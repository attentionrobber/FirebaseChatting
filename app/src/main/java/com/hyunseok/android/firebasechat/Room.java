package com.hyunseok.android.firebasechat;

/**
 * snapShot 데이터를 받을 수 있도록 getter setter 생성한다.
 * Created by Administrator on 2017-03-13.
 */

public class Room {
    String key;
    String title;

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
