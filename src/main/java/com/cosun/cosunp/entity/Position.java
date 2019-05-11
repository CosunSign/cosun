package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/5/9 0009 上午 9:48
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Position implements Serializable {

    private static final long serialVersionUID = 3282382538666102784L;

    private Integer id;
    private String positionName;
    private String positionLevel;
    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 10;
    private int currentPageTotalNum;


    public int getCurrentPageTotalNum() {
        if(this.currentPage!=0)
            return (currentPage - 1) * pageSize;
        return 0;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public int getMaxPage() {
        return maxPage;
    }
}
