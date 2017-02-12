package com.home.rw.mvp.entity;

import java.io.Serializable;

/**
 * Created by cty on 2017/2/6.
 */

public class ContractAfterEntity implements Serializable{
    private int id;
    private String name;
    private String avatar;
    private String letter;
    boolean isSelected;
    boolean isAdded;

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isAdded() {
        return isAdded;
    }
}
