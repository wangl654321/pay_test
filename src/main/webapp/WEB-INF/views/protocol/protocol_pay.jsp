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
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/protocol/saveProtocolPay" >
            <h3>协议支付 请求参数</h3>

            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/pay/protocolSign.do" required="required"/>
            </p>
            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required"  placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="protocolSign" required="required"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/></p>

            <p><label>业务流水号:</label><input name="orderFlowNo" id="orderFlowNo" type="text" placeholder="必填" required="required"/></p>
            <p><label>协议号:</label><input name="protocolNo" id="protocolNo" type="text" placeholder="必填" required="required"/></p>
            <p><label>交易时间:</label>
                <input name="tranDate" id="tranDate" type="text" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" required="required"/>
            </p>
            <p><label>商品名称:</label><input name="goodsName" id="goodsName" type="text" placeholder="必填" required="required"/></p>
            <p><label>业务类型:</label><input name="tranCode" id="tranCode" type="text" placeholder="必填" required="required"/></p>
            <p><label>业务号码:</label><input name="businessCode" id="businessCode" type="text" placeholder="必填" required="required"/></p>
            <p><label>币种:</label><input name="currency" id="currency" type="text" value="CNY" placeholder="必填" required="required"/></p>

            <p><label>客户类型:</label><input name="customerType" id="customerType" type="text" value="P" placeholder="必填" required="required"/></p>
            <p><label>卡折标志:</label>
                <select name="accountType" id = "accountType" required = "required">
                    <option value="0">借记卡</option>
                    <option value="1">存折</option>
                    <option value="2">贷记卡</option>
                    <option value="3">公司账户</option>
                </select>
            </p>
            <p><label>付款人账号:</label><input name="accountNo" id="accountNo" type="text" value="" placeholder="必填" required="required"/></p>
            <p><label>付款人帐户名:</label><input name="accountName" id="accountName" type="text" value="" placeholder="必填" required="required"/></p>
            <p><label>付款人账户银行代码:</label><input name="payBankId" id="payBankId" type="text" value="511C" placeholder="必填" required="required"/></p>8520.,xcz;

            <p><label>付款行所在省代码:</label><input name="provinceNo" id="provinceNo" type="text" value="511C" placeholder="必填" required="required"/></p>
            <p><label>付款行所在城市代码:</label><input name="cityNo" id="cityNo" type="text" value="110114" placeholder="必填" required="required"/></p>

            <p><label>付款人账户开户行名称:</label><input name="payBankName" id="payBankName" type="text" placeholder="必填" required="required"/></p>
            <p><label>付款人手机号:</label><input name="mobileNo" id="mobileNo" type="text" placeholder="必填" required="required"/></p>
            <p><label>交易金额:</label><input name="payAmt" id="payAmt" type="text" placeholder="必填" required="required"/></p>


            <p><label>证件类型:</label><input name="certType" id="certType" type="text" placeholder="必填" required="required"/></p>
            <p><label>证件号码:</label><input name="certNo" id="certNo" type="text" placeholder="必填" required="required"/></p>
            <p><label>客户流水摘要:</label><input name="remark" id="remark" type="text" placeholder="必填" required="required"/></p>
            <p><label>回调地址:</label>
                <input name="notifyUrl" id="notifyUrl" value="http://wanglu654321.wicp.net/test/register/notify" type="text" placeholder="必填"  required="required"/>
            </p>
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