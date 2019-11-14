package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/11/5 0005 下午 2:06
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class QYweixinSend implements Serializable {

    private static final long serialVersionUID = -6907759248335147185L;

    private Integer opencheckindatatype;    //是 	打卡类型。1：上下班打卡；2：外出打卡；3：全部打卡
    private Long starttime;    //是 	获取打卡记录的开始时间。Unix时间戳
    private Long endtime;    //是 	获取打卡记录的结束时间。Unix时间戳
    private List<String> useridlist;    //是 	需要获取打卡记录的用户列表


    public Integer getOpencheckindatatype() {
        return opencheckindatatype;
    }

    public void setOpencheckindatatype(Integer opencheckindatatype) {
        this.opencheckindatatype = opencheckindatatype;
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public List<String> getUseridlist() {
        return useridlist;
    }

    public void setUseridlist(List<String> useridlist) {
        this.useridlist = useridlist;
    }
}
