package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/21 0021 上午 10:36
 * @Modified By:
 * @Modified-date:2018/12/21 0021 上午 10:36
 */
public class DownloadView implements Serializable {

    private static final long serialVersionUID = 8178603164200909341L;
    private Integer id;
    private Integer uId;
    private String userName;
    private String password;
    private String creator;
    private String lastUpdator;
    private String fileName; //文件名称
    private String salor;//业务员
    private String orderNo;//订单编号
    private String projectName;//项目名称
    private String lastUpdateTime;
    private String createTime;
    private Integer totalUpdateNum;
    private String opRight; //操作权限
    private String urlAddr;
    private String seePrivilege; //查看权限  1
    private String updatePrivilege; //修改权限  2
    private String cancelPrivilege; //取消权限  3
    private String flag;

    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 15;
    private int currentPageTotalNum;
    private int preCurrentPage;
    private int aftCurrentPage;

    private Date startNewestSaveDate; //最新上传开始时间
    private Date endNewestSaveDate;//最新上传结束时间
    private String startNewestSaveDateStr;
    private String endNewestSaveDateStr;

    public Date getStartNewestSaveDate() {
        return startNewestSaveDate;
    }

    public void setStartNewestSaveDate(Date startNewestSaveDate) {
        this.startNewestSaveDate = startNewestSaveDate;
    }

    public Date getEndNewestSaveDate() {
        return endNewestSaveDate;
    }

    public void setEndNewestSaveDate(Date endNewestSaveDate) {
        this.endNewestSaveDate = endNewestSaveDate;
    }

    public String getStartNewestSaveDateStr() {
        if(startNewestSaveDateStr!=null&&startNewestSaveDateStr.trim().length()>0)
        return startNewestSaveDateStr.toString()+" 00:00:00";
        return null;
    }

    public void setStartNewestSaveDateStr(String startNewestSaveDateStr) {
        this.startNewestSaveDateStr = startNewestSaveDateStr;
    }

    public String getEndNewestSaveDateStr() {
        if(endNewestSaveDateStr!=null&&endNewestSaveDateStr.trim().length()>0)
        return endNewestSaveDateStr.toString()+" 23:59:59";
        return null;
    }

    public void setEndNewestSaveDateStr(String endNewestSaveDateStr) {
        this.endNewestSaveDateStr = endNewestSaveDateStr;
    }

    public int getPreCurrentPage() {

        return currentPage-1;
    }

    public void setPreCurrentPage(int preCurrentPage) {
        this.preCurrentPage = preCurrentPage;
    }

    public int getAftCurrentPage() {
        return currentPage+1;
    }

    public void setAftCurrentPage(int aftCurrentPage) {
        this.aftCurrentPage = aftCurrentPage;
    }

    public int getCurrentPageTotalNum() {
        return (currentPage-1)*pageSize;
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

    public int getMaxPage() {
        return maxPage;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeePrivilege() {
        if(opRight!=null && opRight.trim().length()>0 && opRight.contains("1")) {
            return "1";
        }
        return seePrivilege;
    }

    public void setSeePrivilege(String seePrivilege) {
        this.seePrivilege = seePrivilege;
    }

    public String getUpdatePrivilege() {
        if (opRight != null && opRight.trim().length() > 0 && opRight.contains("2")) {
            return "2";
        }
        return updatePrivilege;

    }

    public void setUpdatePrivilege(String updatePrivilege) {
        this.updatePrivilege = updatePrivilege;
    }

    public String getCancelPrivilege() {
        if(opRight!=null && opRight.trim().length()>0 && opRight.contains("3")) {
            return "3";
        }
        return cancelPrivilege;
    }

    public void setCancelPrivilege(String cancelPrivilege) {
        this.cancelPrivilege = cancelPrivilege;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastUpdator() {
        return lastUpdator;
    }

    public void setLastUpdator(String lastUpdator) {
        this.lastUpdator = lastUpdator;
    }

    public String getFileName() {
        if(fileName!=null)
            return fileName.trim();
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSalor() {
        if(salor!=null)
            return salor.trim();
        return salor;
    }

    public void setSalor(String salor) {
        this.salor = salor;
    }

    public String getOrderNo() {
        if(orderNo!=null) {
            return orderNo.trim();
        }
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProjectName() {
        if(projectName!=null)
            return projectName.trim();
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getTotalUpdateNum() {
        return totalUpdateNum;
    }

    public void setTotalUpdateNum(Integer totalUpdateNum) {
        this.totalUpdateNum = totalUpdateNum;
    }

    public String getOpRight() {
        return opRight;
    }

    public void setOpRight(String opRight) {
        this.opRight = opRight;
    }

    public String getUrlAddr() {
        return urlAddr;
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr;
    }
}
