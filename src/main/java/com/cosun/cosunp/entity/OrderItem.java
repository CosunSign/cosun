package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/8/13 0013 上午 11:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 8538595894167763085L;

    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
