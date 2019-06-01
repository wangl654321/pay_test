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

        .main {
            width: 1000px;
            margin: 0 auto;
            background: #C0DFFF;
            height: 718px;
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
            $("#fromId").submit()
        }
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/toYoYi/savePreprocess">
            <h3>支付请求 请求参数</h3>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text" value="${yMerchantId}" class="check_required" placeholder="必填"/></p>

            <p><label>订单号:</label><input name="orderNo" id="orderNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>订单金额:</label><input name="orderAmt" id="orderAmt" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>支付币种:</label><input name="curType" id="curType" type="text" value="CNY" class="check_required" placeholder="必填"/></p>

            <p><label>商户返回URL:</label><input name="returnURL" id="returnURL" type="text" class="check_required" value="https://www.baidu.com/" placeholder="必填"/></p>
            <p><label>商户后台通知URL:</label><input name="notifyURL" id="notifyURL" type="text" class="check_required"
                                               value="http://wanglu654321.wicp.net/test/pay/notify" placeholder="必填"/></p>

            <p><label>备注字段:</label><input name="remark" id="remark" type="text" value="测试" placeholder="非必填"/></p>
            <p><label>商户注册用户号:</label><input name="userId" id="userId" type="text" placeholder="非必填"/></p>
            <p><label>商品名称:</label><input name="goodsName" id="goodsName" type="text" placeholder="非必填"/></p>
            <p><label>是否送卡信息:</label><input name="isBind" id="isBind" type="text" placeholder="非必填"/></p>

            <p><label>手机号:</label><input name="mobile" id="mobile" type="text" placeholder="非必填"/></p>
            <p><label>身份证号:</label><input name="certNo" id="certNo" type="text" placeholder="非必填"/></p>
            <p><label>持卡人姓名:</label><input name="userName" id="userName" type="text" placeholder="非必填"/></p>
            <p><label>银行卡号:</label><input name="cardNo" id="cardNo" type="text" placeholder="非必填"/></p>
            <p><label>银行代码:</label><input name="bankType" id="bankType" type="text" placeholder="非必填"/></p>
            <p><label>卡种:</label><input name="cardType" id="cardType" type="text" placeholder="非必填"/></p>

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