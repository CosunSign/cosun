<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="sf"  uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js "></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>文件管理-下载
</title>
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

    function opendownpage() {
        document.forms[1].submit();
    }
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


    function openmanagepage() {
        document.forms[2].submit();
    }

</script>
<body>
<form  action="/fileupdown/upload" method="post" enctype="multipart/form-data" >
    用户名:<input  type="text" id="username"  name="username" value="${username}" onfocus=this.blur()>
    密码:<input type="password"  id="password" name="password" value="${password}" onfocus=this.blur()>
    编号:<input type="text" id="uid" name="uid"  onfocus=this.blur()>

    <input type="button" id="uploadfile" value="上传" onclick="openuploaddiv()">
    <input type="button" id="downloadfile" value="下载" onclick="opendownpage()">
    <input type="button" id="filemanage" value="管理" onclick="openmanagepage()">
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

<form>
    <table class="grid" style="width: 100%">
        <tr>
            <td>编号</td>
            <td>创建者</td>
            <td>最后修改者</td>
            <td>文件名</td>
            <td>业务员</td>
            <td>订单编码</td>
            <td>顶目名称</td>
            <td>最后更新时间</td>
            <td>更新次数</td>
            <td>操作权限</td>
            <td>路径</td>
        </tr>
        <c:forEach items="${downloadViewList}" varStatus="i" begin="0" var="downloadView">
        <tr>
            <td>${i.index+1}</td>
            <td>${downloadView.getCreator()}</td>
            <td>${downloadView.getLastUpdator()}</td>
            <td>${downloadView.getFileName()}</td>
            <td>${downloadView.getSalor()}</td>
            <td>${downloadView.getOrderNo()}</td>
            <td>${downloadView.getProjectName()}</td>
            <td>${downloadView.getLastUpdateTime()}</td>
            <td>${downloadView.getTotalUpdateNum()}</td>
            <td>${downloadView.getOpRight()}</td>
            <td><a href="/fileupdown/downloadfile?filename=${downloadView.getFileName()}&urlname=${downloadView.getUrlAddr()}">${downloadView.getUrlAddr()}</a></td>
        </tr>
        </c:forEach>
    </table>
</form>

<form action="/fileupdown/toprivilegemanagepage">

</form>

</body>
</html>