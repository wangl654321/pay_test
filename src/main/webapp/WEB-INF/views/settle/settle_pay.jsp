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
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/settle/savePreprocess" >
            <h3>(结算通)支付 请求参数</h3>

            <p><label>订单号:</label><input name="orderNo" id="orderNo" type="text" placeholder="订单号" required="required"/></p>

            <p><label>订单金额:</label><input name="orderAmt" id="orderAmt" type="text" placeholder="订单金额" required="required"/></p>
            <p><label>支付币种:</label><input name="curType" id="curType" type="text" value="CNY" placeholder="支付币种" required="required"/></p>

            <p><label>商户返回URL:</label><input name="returnURL" id="returnURL" type="text" class="check_required" value="https://www.baidu.com/" placeholder="必填"/></p>
            <p>
                <label>商户后台通知URL:</label>
                <input name="notifyURL" id="notifyURL" type="text" value="http://wanglu654321.wicp.net/test/pay/notify" placeholder="商户后台通知URL" required="required"/>
            </p>

            <p><label>收款账户:</label><input name="recAcct" id="recAcct" type="text" value="" placeholder="必填" required="required"/></p>
            <p><label>付款账户:</label><input name="payAcct" id="payAcct" type="text" value="" placeholder="非必填"/></p>

            <p><label>收款方手续费金额:</label><input name="payFeeAmt" id="payFeeAmt" type="text" placeholder="非必填"/></p>
            <p><label>付款方手续费金额:</label><input name="recFeeAmt" id="recFeeAmt" type="text" placeholder="非必填"/></p>

            <p><label>商品名称:</label><input name="goodsName" id="goodsName" type="text" placeholder="非必填"/></p>
            <p><label>商品数量:</label><input name="goodsNum" id="goodsNum" type="text" placeholder="非必填"/></p>

            <p><label>交易类型:</label>
                <select name="transactionType" id="transactionType">
                    <option value="1">实物交易</option>
                    <option value="2">虚拟交易</option>
                </select>
            </p>

            <p><label>预留域:</label><input name="backup" id="backup" type="text" placeholder="非必填"/></p>
            <p>
                <input type='submit' value='提 交'/>
                <input type='button' value='返 回' onclick="javascript:history.go(-1)"/>
            </p>
        </form>
    </div>

</div>
</body>
<jsp:include page="/base/tail.jsp"/>
</html>