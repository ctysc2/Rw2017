package com.home.rw.mvp.entity.base;

/**
 * Created by cty on 2017/2/24.
 */

public class BaseApprovementEntity {
    int currPage;
    long totalElements;
    int totalPages;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
