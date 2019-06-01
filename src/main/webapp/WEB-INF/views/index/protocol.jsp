<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base/base.jsp" %>
<html>
<link rel='stylesheet' href="${ctx}/css/base.css"/>
<link rel='stylesheet' href="${ctx}/css/index.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API测试首页</title>
<head>
</head>
<body>
<!--  头部 -->
<div class="header">
    <div class="logo">
        <img src="${ctx}/images/back.png" onclick="javascript:history.go(-1)"/>
        <span class="border"></span>
        <span class="text">协议支付-接口API测试</span>
    </div>
</div>
<!-- 主页面 -->
<div class="main">
    <ul>
        <li>
            <a href="${ctx}/protocol/protocolSign">支付协议签约</a>
        </li>
        <li>
            <a href="${ctx}/protocol/protocolSignQuery">签约结果查询</a>
        </li>
        <li>
            <a href="${ctx}/protocol/protocolPay">协议支付</a>
        </li>
        <li>
            <a href="${ctx}/protocol/tranResultQry">交易结果查询</a>
        </li>
    </ul>
</div>
<!--尾部-->
<div class="footer">
    <p>
        <span>关于我们</span>
        <span>合作伙伴</span>
        <span>商务合作</span>
        <span>联系我们</span>
    </p>
    <p class="mark">Copyright © 2011-<%=new SimpleDateFormat("yyyy").format(new Date())%> 接口测试项目 - Powered By JeeSite V1.0</p>
</div>
</body>
</html>