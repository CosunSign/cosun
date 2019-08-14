package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2019/8/13 0013 上午 11:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class OrderHead implements Serializable {

    private static final long serialVersionUID = 8581338015386967740L;

    private Integer id;
    private Integer singleOrProject; //代表是单项类还是项目类
    private String orderNo; //客户单号
    private String productName; //品名
    private String orderTimeStr; //下单时间
    private String deliverTimeStr; //交货时间
    private String orderSetNum;//订单套数
    private String SalorNo; //业务员
    private Date orderTime;
    private Date deliverTime;
    private String engName;

    private String salor;

    //item
    private Integer orderHeadId;//关联Id
    private String productBigType; //产品大类
    private String productMainShape; //产品主体
    private String newFinishProudNo; //新成品编号
    private Double productSize; //产品尺寸
    private Double edgeHightSize; //边高尺寸
    private String mainMateriAndArt;//主体材质需求及工艺
    private String backInstallSelect;//背部安装选项
    private String electMateriNeeds;//电子类辅料需求
    private String installTransfBacking;//安装运输包装
    private String otherRemark;//其它说明

    private Integer itemId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getSalor() {
        return salor;
    }

    public void setSalor(String salor) {
        this.salor = salor;
    }

    public Integer getOrderHeadId() {
        return orderHeadId;
    }

    public void setOrderHeadId(Integer orderHeadId) {
        this.orderHeadId = orderHeadId;
    }

    public String getProductBigType() {
        return productBigType;
    }

    public void setProductBigType(String productBigType) {
        this.productBigType = productBigType;
    }

    public String getProductMainShape() {
        return productMainShape;
    }

    public void setProductMainShape(String productMainShape) {
        this.productMainShape = productMainShape;
    }

    public String getNewFinishProudNo() {
        return newFinishProudNo;
    }

    public void setNewFinishProudNo(String newFinishProudNo) {
        this.newFinishProudNo = newFinishProudNo;
    }

    public Double getProductSize() {
        return productSize;
    }

    public void setProductSize(Double productSize) {
        this.productSize = productSize;
    }

    public Double getEdgeHightSize() {
        return edgeHightSize;
    }

    public void setEdgeHightSize(Double edgeHightSize) {
        this.edgeHightSize = edgeHightSize;
    }

    public String getMainMateriAndArt() {
        return mainMateriAndArt;
    }

    public void setMainMateriAndArt(String mainMateriAndArt) {
        this.mainMateriAndArt = mainMateriAndArt;
    }

    public String getBackInstallSelect() {
        return backInstallSelect;
    }

    public void setBackInstallSelect(String backInstallSelect) {
        this.backInstallSelect = backInstallSelect;
    }

    public String getElectMateriNeeds() {
        return electMateriNeeds;
    }

    public void setElectMateriNeeds(String electMateriNeeds) {
        this.electMateriNeeds = electMateriNeeds;
    }

    public String getInstallTransfBacking() {
        return installTransfBacking;
    }

    public void setInstallTransfBacking(String installTransfBacking) {
        this.installTransfBacking = installTransfBacking;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public String getSalorNo() {
        return SalorNo;
    }

    public void setSalorNo(String salorNo) {
        SalorNo = salorNo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSingleOrProject() {
        return singleOrProject;
    }

    public void setSingleOrProject(Integer singleOrProject) {
        this.singleOrProject = singleOrProject;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public String getDeliverTimeStr() {
        return deliverTimeStr;
    }

    public void setDeliverTimeStr(String deliverTimeStr) {
        this.deliverTimeStr = deliverTimeStr;
    }

    public String getOrderSetNum() {
        return orderSetNum;
    }

    public void setOrderSetNum(String orderSetNum) {
        this.orderSetNum = orderSetNum;
    }
}
