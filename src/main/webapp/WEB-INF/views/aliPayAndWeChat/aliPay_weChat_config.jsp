<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/base/base.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>模拟商户页面</title>
    <link rel='stylesheet' href="${ctx}/css/base.css"/>
    <link rel='stylesheet' href="${ctx}/css/index.css"/>
    <script src="${ctx}/scripts/jquery-3.0.0.min.js"></script>
    <style type="text/css">
        .quickAPI h3 {
            height: 0px;
        }
    </style>
    <script type="text/javascript">
        function subFrom() {
            var regUrl = $("#regUrl").val();
            $("#fromId").attr("action",regUrl);
            $("#fromId").submit()
        }
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post">

            <h3>(微信支付宝)支付请求 请求确认</h3>
            <p><label>测试地址:</label><input id="regUrl" type="text" value="${test}/pay/anonymousPayOrder.do"   placeholder="必填"/></p>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text" value="${merchantId}" placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="anonymousPayOrder" placeholder="必填"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/></p>
            <p><label>交易数据:</label>
                <textarea name="tranData" id="tranData" type="text" style="width: 297px;height: 90px;" placeholder="必填">${tranData}</textarea>
            </p>
            <br> <br> <br>
            <p><label>订单签名数据:</label>
                <textarea name="merSignMsg" id="merSignMsg" type="text" style="width: 297px;height: 90px;" placeholder="必填">${merSignMsg}</textarea>
            </p>
            <br><br><br>
            <p>
                <input type='button' value='提 交' onclick="subFrom()"/>
                <input type='button' value='返 回' onclick="javascript:history.go(-1)"/>
            </p>
        </form>
    </div>

</div>
</body>
<jsp:include page="/base/tail.jsp"/>
</html>