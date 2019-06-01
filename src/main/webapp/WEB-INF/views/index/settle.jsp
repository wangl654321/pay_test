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
        <span class="text">接口API测试</span>
    </div>
</div>
<!-- 主页面 -->
<div class="main">
    <ul>
        <li>
            <a href="${ctx}/settle/preprocess">(结算通)支付</a>
        </li>
        <li>
            <a href="${ctx}/settle/sendGoodsNotify">(结算通)发货通知</a>
        </li>
        <li>
            <a href="${ctx}/settle/secOrderQuery">(结算通)订单查询</a>
        </li>
        <li>
            <a href="${ctx}/settle/secOrderClose">(结算通)订单关闭</a>
        </li>
        <li>
            <a href="${ctx}/settle/refundSecOrder">(结算通)退款</a>
        </li>
        <li>
            <a href="${ctx}/settle/secOrderDelay">(结算通)延期</a>
        </li>
        <li>
            <a href="${ctx}/settle/secOrderConfirm">(结算通)确认收货</a>
        </li>
        <li>
            <a href="${ctx}/settle/securityOrderDelay">(结算通)延期确认</a>
        </li>
        <li>
            <a href="${ctx}/settle/secOrderDelay">(结算通)延期拒绝</a>
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
    <p class="mark">Copyright © 2011-2018 接口测试项目 - Powered By JeeSite V1.0</p>
</div>
</body>
</html>