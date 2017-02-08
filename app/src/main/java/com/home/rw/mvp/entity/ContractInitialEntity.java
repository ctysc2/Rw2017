package com.home.rw.mvp.entity;

/**
 * Created by cty on 2017/2/6.
 */

public class ContractInitialEntity {
    private String name;
    private String number;

    public ContractInitialEntity(String name, String number) {
        this.name = name;
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
}
