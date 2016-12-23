package com.home.rw.event;

/**
 * Created by cty on 2016/12/21.
 */

public class BeforeReadEvent {

    private int number;

    public BeforeReadEvent(int number){
        this.number = number;

    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
