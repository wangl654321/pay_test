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
        <form id="fromId" method="post" action="${ctx}/withholding/saveWithholdSignCancel" >
            <h3>代扣解约 请求参数</h3>

            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/pay/withholdSignCancel.do" required="required"/>
            </p>
            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required"  placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="withholdSignCancel" required="required"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/></p>

            <p><label>签约协议号:</label><input name="orderNo" id="orderNo" type="text" placeholder="协议号" required="required"/></p>
            <p><label>银行卡号:</label><input name="bankAccountNo" id="bankAccountNo" type="text" placeholder="银行卡号" required="required"/></p>

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