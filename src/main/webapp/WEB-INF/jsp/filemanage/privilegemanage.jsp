<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="sf"  uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js "></script>
<>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>系统管理</title>
</head>
 <style type="text/css">
     table.grid {
            font-family: verdana,arial,sans-serif;
             font-size:11px;
             color:#333333;
             border-width: 1px;
             border-color: #666666;
             border-collapse: collapse;
         }
     table.grid th {
                border-width: 1px;
                padding: 8px;
                border-style: solid;
                border-color: #666666;
                background-color: #dedede;
            }
     table.grid td {
                border-width: 1px;
                padding: 8px;
                border-style: solid;
                border-color: #666666;
                background-color: #ffffff;
            }
     </style>
<script language="JavaScript">

    $(function(){
        var upstatus = $("#uploadflag").val();
        if(upstatus==1){
            $("#uploadflag").html("<h5><font color=\"#FF0000\">文件上传成功</font></h5>");
            $("#showuploadstatusmessage").show();
        }else if(upstatus==0){
            $("#uploadflag").html("<h5><font color=\"#FF0000\">文件上传失败</font></h5>");
            $("#showuploadstatusmessage").show();
        }else if(upstatus==-1){
            $("#uploadflag").html("<h5><font color=\"#FF0000\">抱歉,上传文件不能为空</font></h5>");
            $("#showuploadstatusmessage").show();
        }
    });

    function openchecksubmitfilediv() {
        $("#showchecksubmitdiv").show();
    }

    function openuploaddiv() {
        $("#uploadbuttondiv").show();
    }

    function checksalormessage() {
        var salorid = $("#salorid").val();
        var orderid = $("#orderid").val();
        var projectid = $("#projectid").val();

        document.forms[0].submit();

        if(salorid.trim()==""||salorid.getLength()==0||orderid.trim()==""
            ||orderid.getLength()==0||projectid.trim()==""||projectid.getLength()==0){
            $("#showtipmessage").html("<h5><font color=\"#FF0000\">文件上传信息不能为空</font></h5>");
            $("#showtipmessage").show();
        }

    }

    function submitprivilegestatus() {
        var privilelist=new Array()
        var selectuser = $("#selectuser").val();
        var trs = $("#privilegetable tr");
        privilelist[0]= selectuser;
        for (var i = 1; i < trs.length; i++) {
            var trid = trs[i].id;//privilegenum
            privilelist[i]=trid;
            $('input[name="privilegenum' + trid + '"]:checked').each(function(){//拼接是重点  容易拼接出错
                privilelist[i]+=","+$(this).val();
            });
        }
        $.ajax({
            data:JSON.stringify(privilelist),
            type:"POST",
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8', //内容类型
            contentType :"application/json",  // 这种方式必须要
            async : false,//是否异步请求
            traditional: true,
            url:"/fileupdown/updateandsaveprivilegestatus",
            success:function(msg){
                var data = eval(msg);//将msg化为数;
                var num = 0;
                var tr;
                var td;
                var tbody = $("#showlistbody");
                tbody.html("");
                var vililegeflags;
                $.each(data, function (num){

                    tr = $("<tr id='"+data[num].id+"'></tr>");
                    tr.append("<td>" + (num + 1) + "</td>");
                    tr.append("<td>" + isNull(data[num].creator) + "</td>");
                    tr.append("<td>" + isNull(data[num].lastUpdator)  + "</td>");
                    tr.append("<td>" + isNull(data[num].fileName)  + "</td>");
                    tr.append("<td>" + isNull(data[num].salor)  + "</td>");
                    tr.append("<td>" + isNull(data[num].orderNo)  + "</td>");
                    tr.append("<td>" + isNull(data[num].projectName)  + "</td>");
                    tr.append("<td>" + isNull(data[num].createTime)  + "</td>");
                    tr.append("<td>" + isNull(data[num].lastUpdateTime)  + "</td>");
                    td = "<td>";

                    vililegeflags = data[num].opRight;
                    if(isNull(vililegeflags)!=""){
                        td = "<td>";
                    if(vililegeflags.indexOf("1") > -1){
                        td += ('<input name="privilegenum'+data[num].id+'" checked="checked" type="checkbox" value="1" />查看');
                        }else {
                        td += ('<input name="privilegenum'+data[num].id+'"  type="checkbox" value="1" />查看');
                    }
                    if(vililegeflags.indexOf("2") > -1){
                        td += ('<input name="privilegenum'+data[num].id+'" checked="checked" type="checkbox" value="2" />查看');
                    }else{
                        td += ('<input name="privilegenum'+data[num].id+'"  type="checkbox" value="2" />查看');
                    }
                    if(vililegeflags.indexOf("3") > -1){
                        td += ('<input name="privilegenum'+data[num].id+'" checked="checked" type="checkbox" value="3" />查看');
                    }else{
                        td += ('<input name="privilegenum'+data[num].id+'"  type="checkbox" value="3" />查看');
                    }
                        td += "</td>";
                    } else {
                        td = "<td><input name='privilegenum${data[num].id}'  type='checkbox' value='1'>查看";
                        td += "<input name='privilegenum${data[num].id}'  type='checkbox' value='2'>修改";
                        td += "<input name='privilegenum${data[num].id}'  type='checkbox' value='3'>取消</td>";
                    }

                    tr.append(td);
                    tr.appendTo(tbody);
                });

            },
            error:function(data){
                alert("失败");
            }

        });

    }

    function isNull(arg1)
    {
        if(arg1 == "null" || arg1 == null) {
            return "";
        }
        return arg1;
        //return !arg1 && arg1!==0 && typeof arg1!=="boolean"?true:false;
    }

</script>
<>
<form  action="/fileupdown/upload" method="post" enctype="multipart/form-data" >
    用户名:<input  type="text" id="username"  name="username" value="${username}" onfocus=this.blur()>
    密码:<input type="password"  id="password" name="password" value="${password}" onfocus=this.blur()>
    编号:<input type="text" id="uid" name="uid"  onfocus=this.blur()>

    <input type="button" id="uploadfile" value="上传" onclick="openuploaddiv()">
    <input type="button" id="downloadfile" value="下载" onclick="opendowndiv()">
    <input type="button" id="filemanage" value="管理" onclick="openmanagediv()">
    <br/>
    <br/>
    <div id="uploadbuttondiv" style="display:none;">
        <input type="file" id="file" name="file"  >
        <input id="uploadflag" name="uploadflag" type="hidden" value="${flag}">
        &nbsp;&nbsp;&nbsp;
        <input type="button" id="submitfile" onclick="openchecksubmitfilediv()" value="提交"></input>
        <div id="showuploadstatusmessage" style="display:none;">
        </div>

    </div>
    <div id="showchecksubmitdiv" style="display:none;" >
        业务员:<input id="salorid" name="salorname"><br/>
        订单编号:<input id="orderid" name="ordername"><br/>
        项目名称:<input id="projectid" name="projectname"><br/>
        <input type="button" onclick="checksalormessage()" value="确认提交">
        <br/><div id="showtipmessage" style="display:none;"></div>

    </div>

</form>

<form action="/fileupdown/download">
    用户 <select name="selectuser" id="selectuser">
    <c:forEach items="${userInfos}" varStatus="i" begin="0" var="user">
        <option value="${user.uId}">${user.userName}</option>
    </c:forEach>
</select>
    <input type="button" onclick="submitprivilegestatus()" value="确认">
</form>
<br/>
<br/>
<form action="/fileupdown/updateandsaveprivilegestatus" method="post">
    <table class="grid" style="width: 100%" id="privilegetable">
        <thead>
        <tr>
            <td>编号</td>
            <td>创建者</td>
            <td>最后修改者</td>
            <td>文件名</td>
            <td>业务员</td>
            <td>订单编码</td>
            <td>顶目名称</td>
            <td>创建时间</td>
            <td>更新时间</td>
            <td>权限设置</td>
        </tr>
        </thead>
        <tbody id="showlistbody">
        <c:forEach items="${downloadViews}" varStatus="i" begin="0" var="downloadView">
        <tr id="${downloadView.id}">
            <td>${i.index+1}</td>
            <td>${downloadView.getCreator()}</td>
            <td>${downloadView.getLastUpdator()}</td>
            <td>${downloadView.getFileName()}</td>
            <td>${downloadView.getSalor()}</td>
            <td>${downloadView.getOrderNo()}</td>
            <td>${downloadView.getProjectName()}</td>
            <td>${downloadView.createTime}</td>
            <td>${downloadView.getLastUpdateTime()}</td>
            <td>
                <input name="privilegenum${downloadView.id}"  type="checkbox"  <c:if test="${fn:contains(downloadView.opRight,'1')}">checked="checked"</c:if> value="1">查看
                    <input name="privilegenum${downloadView.id}"  type="checkbox" <c:if test="${fn:contains(downloadView.opRight,'2')}">checked="checked"</c:if> value="2">修改
                    <input name="privilegenum${downloadView.id}"  type="checkbox" <c:if test="${fn:contains(downloadView.opRight,'3')}">checked="checked"</c:if> value="3">取消

            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</form>

</body>
</html>