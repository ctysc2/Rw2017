package com.home.rw.mvp.entity.message;

import java.io.Serializable;

/**
 * Created by cty on 2017/3/17.
 */

public class TopicCommonEntity implements Serializable{
    private String imgs;
    private String title;
    private String id;
    private String content;
    private String pubTime;
    private String readed;
    private String promulgator;

    public String getImgs() {
        return imgs;
    }

    public void setPromulgator(String promulgator) {
        this.promulgator = promulgator;
    }

    public String getPromulgator() {
        return promulgator;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getReaded() {
        return readed;
    }

    public void setReaded(String readed) {
        this.readed = readed;
    }
}
