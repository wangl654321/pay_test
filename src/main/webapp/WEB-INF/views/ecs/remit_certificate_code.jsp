<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0 user-scalable=no"/>
    <title>图片返回</title>
    <script src="${ctx}/scripts/jquery-3.0.0.min.js"></script>
</head>
<body style="text-align:center;">
<div style="color:red;width:500px;margin-left:auto;margin-right:auto;">
    返回结果：${msg}
    <br/>
</div>

<br/>
<br/>
<center>

    返回图片: <img src="${ctx}/ecs/imgData" alt="" style="width: 800px;height: 600px">
    <input type='button' value='返 回' onclick="javascript:history.go(-1)"/>
</center>
</body>
</html>