package com.cosun.cosunp.interceptor;

/**
 * @author:homey Wong
 * @Date: 2019/8/7  上午 8:50
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class aaaa {

    //22.77200698852539,114.3155288696289
    //ZzGk5eR49FPNwxhXzbVWXjDsGBBXTA6V

    //http://api.map.baidu.com/reverse_geocoding/v3/?ak=ZzGk5eR49FPNwxhXzbVWXjDsGBBXTA6V&output=json&coordtype=wgs84ll&location=22.77200698852539,114.3155288696289

//
//    整理后:
    //=============================业务模块=======================================
//      1.业务模块,订单的增（业务图纸+订单信息）    完成
//      ，改，                                      完成
//        查                                        完成
//        功能
//        下发，终止，暂停，完成 状态               完成
//        订单编号根据登录名自动生成                完成
//        PDF打印功能                               完成
//        订单图纸上传功能                          完成
//        订单下发后系统推送给PMC                   等待
//      2.项目类的订单
//           项目类的单如果是分批下单需要进行更细的项目名称编写；
    //   8-14 下午开始
//        不分批？
//       （？？一样可以指定时间段才能下订单）
//        命名是什么命名，订单？项目？
//        注意 命名规则： 文件类型（？？）+日期+项目名称+订单号+业务员名字，


    //================================PMC模块============================================
    //    而后PMC根据订单需求表和图纸文件确认人员安排与完成时间，
    //    确认是否需要提前买材料，需要外发（五种文件里面需要哪几种）；
    //    a)线上进行时：
    //    iii.PMC完成后系统推送消息给对应设计师；
    //    iv.PMC：指定人员与完成时间、确认修改流程、更改完成时间
    // =============================分界线===================================
    //


//    iv.设计师设计制作图纸，后续产出有制作图纸、BOM表、激光文件、雕刻文件、外发文件，
//    完成制定图纸的上传确认；
//    v.消息推送设计图纸等给业务，把BOM表推送给PMC，提示图纸完成，业务自己确认的4个小时之内完成
//    ，需要客户确认的两天之内反馈，消息推送给PMC部门，PMC部门根据是否有修改以及修改工作量做下一步安排；
//    b)作为对象的操作：
//    i.管理员：设定管理员、业务员、设计师、PMC的角色与权限；
//    ii.业务员：下发订单、修改订单、暂停订单、终止订单、确认完成、动作描述。
//    iii.设计师：深化、制作图纸、修改图纸；
//    iv.PMC：指定人员与完成时间、确认修改流程、更改完成时间
//    v.系统：消息推送
//    c)作为数据的操作
//    i.订单信息【订单需求表（产品数量、交期、类别、尺寸大小、大致工艺描述等）和图纸文件】
//             【制作组别、设计师、设计完成时间】：新增、修改、暂停、终止；
//    ii.制作图纸【制作图纸、BOM表、激光文件、雕刻文件、外发文件】：新增、修改、暂停、终止；


}
