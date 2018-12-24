<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="sf"  uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js "></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>登录界面</title>
</head>
<script language="JavaScript">


    $(function(){
        var flag=$("#flag").val();
        if(flag=="false"){
            $("#showwrongmessage").html(" <h1><font color=\"#FF0000\">用户名与密码借误</font></h1>");
            $("#username").val("");
            $("#password").val("")
            $("#uid").val("");
        }else{
            $("#showwrongmessage").html("");
        }
    });
</script>
<body>


<form id="loginform" action="/account/login" >
用户名:<input  type="text" id="username"  name="username">
密码:<input type="password"  id="password" name="password">
编号:<input type="text" id="uid" name="uid">
     <input type="submit" value="登录" >
     <input type="hidden" id="flag" value="${flag}">

<div id="showwrongmessage" class="noseediv" >


</div>
</form>

</body>
</html>