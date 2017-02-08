package com.home.rw.utils;

import com.home.rw.mvp.entity.ContractAfterEntity;

import java.util.Comparator;

/**
 * Created by cty on 2017/2/6.
 */

public class PinyinComparator implements Comparator<ContractAfterEntity> {


    public static PinyinComparator instance = null;

    public static PinyinComparator getInstance() {
        if (instance == null) {
            instance = new PinyinComparator();
        }
        return instance;
    }

    public int compare(ContractAfterEntity o1, ContractAfterEntity o2) {
        if (o1.getLetter().equals("@")
                || o2.getLetter().equals("#")) {
            return -1;
        } else if (o1.getLetter().equals("#")
                || o2.getLetter().equals("@")) {
            return 1;
        } else {
            return o1.getLetter().compareTo(o2.getLetter());
        }
    }
}
