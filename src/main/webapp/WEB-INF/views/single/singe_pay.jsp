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
            height: 768px;
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
            $("#merFlowNo").val(rand);

            var recAcctBankNo = parseInt(Math.random() * 100000);
            recAcctBankNo = 100000000 + recAcctBankNo;
            $("#recAcctBankNo").val(recAcctBankNo);

            var rand = parseInt(Math.random() * 100) + 1;
            rand = "0." + rand;
            $("#orderAmt").val(rand);
        })

        function subFrom() {
            var flag = false;
            $(".check_required").each(function(){
                var id =  this.id
                var value =  $("#" + id).val();
                if(value == ''){
                    alert(id + "为空")
                    flag = true;
                    return false
                }
            });
            if(flag){
                return false;
            }

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
        <form id="fromId" method="post" action="${ctx}/single/saveSinglePay" >
            <h3>单笔汇款接口 请求参数</h3>

            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/pay/danbihuikuan.do" required="required"/>
            </p>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required"  placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="danbihuikuan"  placeholder="必填"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/></p>

            <p><label>收款账户:</label><input name="recAcct" id="recAcct" type="text" value="" placeholder="必填" class="check_required"/></p>
            <p><label>付款账户:</label><input name="payAcct" id="payAcct" type="text" value="" placeholder="必填" class="check_required"/></p>
            <p><label>汇款金额:</label><input name="orderAmt" id="orderAmt" type="text" placeholder="订单金额" placeholder="必填" class="check_required"/></p>

            <p><label>收款客户名称:</label><input name="accountName" id="accountName" type="text" placeholder="必填" class="check_required"/></p>
            <p><label>收款银行:</label><input name="bankName" id="bankName" type="text" placeholder="必填" class="check_required"/></p>


            <p><label>支行名称:</label><input name="sonName" id="sonName" type="text" placeholder="非必填"/></p>
            <p><label>原汇款流水号:</label><input name="tranFlowNo" id="tranFlowNo" type="text" placeholder="非必填"/></p>
            <p><label>摘要:</label><input name="remark" id="remark" type="text" placeholder="非必填"/></p>
            <p><label>收款账户类型:</label>
                <select name="userType" id="userType">
                    <option value="0">单位</option>
                    <option value="1">个人</option>
                </select>
            </p>
            <p><label>同城异地标志:</label>
                <select name="bankflag" id="bankflag">
                    <option value="0">同城</option>
                    <option value="1">异地</option>
                </select>
            </p>

            <p><label>联行行号:</label><input name="recAcctBankNo" id="recAcctBankNo" type="text" placeholder="必填" class="check_required"/></p>
            <p><label>商户流水号:</label><input name="merFlowNo" id="merFlowNo" type="text" placeholder="必填" class="check_required"/></p>

            <p><label>访问ip:</label><input name="userIp" id="userIp" value="127.0.0.1" type="text" placeholder="必填"  class="check_required"/></p>
            <p><label>银行卡种:</label>
                <select name="cardType" id="cardType">
                    <option value="0">借记卡</option>
                    <option value="1">贷记卡</option>
                    <option value="2">准贷记卡/公务员卡</option>
                    <option value="9">对公户</option>
                </select>
            </p>
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