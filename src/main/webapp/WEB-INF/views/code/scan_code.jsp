<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0 user-scalable=no"/>
    <title>网银支付</title>
    <script src="${ctx}/scripts/jquery-3.0.0.min.js"></script>
</head>
<body style="text-align:center;">
<div style="color:red;width:500px;margin-left:auto;margin-right:auto;">
    二维码地址：${code}
    <br/>
</div>
<div>
    <c:if test="${not empty map}">
        返回参数: ${map}
    </c:if>
    <br/>
    生成二维码工具：http://cli.im/
</div>
<br/>
<br/>
<center>
    <c:if test="${not empty code}">
        <img alt="二维码" src="${ctx}/aliPayAndWeChatCode?code=${code}">
    </c:if>
    <c:if test="${empty code}">
        <div style="color:red;width:500px;margin-left:auto;margin-right:auto;">
            返回参数: ${resultDesc}
        </div>
    </c:if>
    <input type='button' value='返 回' onclick="javascript:history.go(-1)"/>
</center>
</body>
</html>