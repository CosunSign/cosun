<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="sf"  uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js "></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>文件管理</title>
</head>
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

    function openuploadpage() {
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

    function opendownpage() {
        document.forms[1].submit();
    }

    function openmanagepage() {
        document.forms[2].submit();
    }

</script>
<body>
<form  action="/fileupdown/upload" method="post" enctype="multipart/form-data" >
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    用户名:<input  type="text" id="username"  name="username" value="${username}" onfocus=this.blur()>
    密码:<input type="password"  id="password" name="password" value="${password}" onfocus=this.blur()>
    编号:<input type="text" id="uid" name="uid"  onfocus=this.blur()>

    <input type="button" id="uploadfile" value="上传" onclick="openuploadpage()">
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
<form action="/fileupdown/download">
    <input  type="hidden"  name="username" value="${username}" onfocus=this.blur()>
    <input type="hidden"   name="password" value="${password}" onfocus=this.blur()>
</form>

<form action="/fileupdown/toprivilegemanagepage">
    <input  type="hidden"  name="username" value="${username}" onfocus=this.blur()>
    <input type="hidden"   name="password" value="${password}" onfocus=this.blur()>

</form>
</body>
</html>