package com.ini.data.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Somnus`L on 2017/5/4.
 */
public class BaseSearchVO {

    private String orderBy;
    private String currentPage;

    @Value("${app.query.pagesize}")
    private int pageSize;


    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public String getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }


}