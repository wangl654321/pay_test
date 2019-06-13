<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/base/base.jsp" %>
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

        .main {
            width: 1000px;
            margin: 0 auto;
            background: #C0DFFF;
            height: 718px;
        }
        {
            width: 120px;
            height: 35px;
            background: #4287f5;
            color: white;
            margin-left: 70px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var rand = parseInt(Math.random() * 100000);
            rand = 100000000 + rand;
            $("#orderNo").val(rand);

            var merRefundOrderFlowNo = parseInt(Math.random() * 100000);
            merRefundOrderFlowNo = 100000000 + merRefundOrderFlowNo;
            $("#merRefundOrderFlowNo").val(merRefundOrderFlowNo);
        })
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/withholding/saveRefundOrder" >
            <h3>代扣退款 请求参数</h3>

            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/pay/refundOrder.do" required="required"/>
            </p>
            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required"  placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="withholdRefund" required="required"/></p>

            <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/></p>

            <p><label>支付订单号:</label><input name="orderNo" id="orderNo" type="text" placeholder="支付订单号" required="required"/></p>
            <p><label>退款金额:</label><input name="refundAmt" id="refundAmt" type="text" placeholder="退款金额" required="required"/></p>
            <p><label>通知地址:</label><input name="notifyURL" id="notifyURL" value="http://kang115326.xicp.net:13829/test/pay/notify" type="text"class="check_required"  placeholder="非必填"/></p>
            <p><label>退款订单号:</label><input name="merRefundOrderFlowNo" id="merRefundOrderFlowNo" type="text" placeholder="退款订单号" required="required"/></p>
            <p>
                <input id="submitForm" type='submit' value='提 交'/>
                <input type='button' value='返 回' onclick="javascript:history.go(-1)"/>
            </p>
        </form>
    </div>

</div>
</body>
<jsp:include page="/base/tail.jsp"/>
</html>