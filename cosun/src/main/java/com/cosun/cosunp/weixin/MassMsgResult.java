package com.cosun.cosunp.weixin;

/**
 * @author:homey Wong
 * @Date: 2019/9/26 0026 下午 3:56
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MassMsgResult {

    private Integer id;

    private String type; //媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），次数为news，即图文消息
    private String msg_id;
    private String msg_data_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_data_id() {
        return msg_data_id;
    }

    public void setMsg_data_id(String msg_data_id) {
        this.msg_data_id = msg_data_id;
    }
}
