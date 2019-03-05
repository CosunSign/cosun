package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/3/5 0005 上午 11:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 3941220683407513983L;
    private Integer id;
    private String name;
    private Integer sex;
    private Integer depno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getDepno() {
        return depno;
    }

    public void setDepno(Integer depno) {
        this.depno = depno;
    }
}
