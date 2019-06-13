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

            var rand = parseInt(Math.random() * 100) + 1;
            rand = "0." + rand;
            $("#orderAmt").val(rand);
        })

        function subFrom() {
            $.post($("#fromId").attr("action"), $("#fromId").serialize(), function (data) {
                if (data == "") {
                    alert("未知异常");
                    return false;
                }
                alert(data);
            });
        }
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/withholding/saveWithholdPay" >
            <h3>代扣支付 请求参数</h3>

            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/pay/withholdPay.do" required="required"/>
            </p>
            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required"  placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="withholdPay" required="required"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/></p>

            <p><label>订单号:</label><input name="orderNo" id="orderNo" type="text" placeholder="订单号" required="required"/></p>
            <p><label>代扣款项:</label>
                <select name="billType" id = "billType" required = required>
                    <option value="01">移动电话</option>
                    <option value="02">固定电话</option>
                    <option value="03">水费</option>
                    <option value="04">电费</option>

                    <option value="05">煤气费</option>
                    <option value="06">社保</option>
                    <option value="07">小灵通</option>
                    <option value="08">信用卡还款</option>
                </select>
            </p>

            <p><label>交易金额:</label><input name="orderAmt" id="orderAmt" type="text" placeholder="交易金额" required="required"/></p>
            <p><label>银行卡号:</label><input name="bankAccountNo" id="bankAccountNo" type="text" placeholder="银行卡号" required="required"/></p>
            <p><label>协议号:</label><input name="protocolId" id="protocolId" type="text" placeholder="协议号" required="required"/></p>
            <p><label>交易类型:</label>
                <select name="transactionType" id = "transactionType" required = required>
                    <option value="1">实物交易</option>
                    <option value="2">虚拟交易</option>
                </select>
            </p>

            <p><label>商品名称:</label><input name="goodsName" id="goodsName" type="text" placeholder="必填" value="测试商品" required="required"/></p>
            <p><label>商品编号:</label><input name="goodsNum" id="goodsNum" type="text" placeholder="非必填"/></p>
            <p><label>商品单价:</label><input name="goodsPrice" id="goodsPrice" type="text" placeholder="非必填"/></p>
            <p><label>客户端IP:</label><input name="clientIp" id="transIP" type="text" value="127.0.0.1" placeholder="必填"/></p>

            <p><label>通知地址:</label><input name="backUrl" id="backUrl" value="http://kang115326.xicp.net:13829/order/pay/notify" type="text"class="check_required"  placeholder="非必填"/></p>
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