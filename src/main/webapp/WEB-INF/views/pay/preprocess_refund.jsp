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
        $(function () {
            var rand = parseInt(Math.random() * 100000);
            rand = 100000000 + rand;
            $("#merRefundOrderFlowNo").val(rand);
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
        <form id="fromId" method="post" action="${ctx}/toYoYi/savePreprocessRefund">
            <h3>支付退款 请求参数</h3>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text" class="check_required" value="${yMerchantId}" placeholder="必填"/></p>
            <p><label>订单号:</label><input name="orderNo" id="orderNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>退款金额:</label><input name="refundAmt" id="refundAmt" type="text" class="check_required" placeholder="必填"/></p>


            <p><label>商户后台通知URL:</label><input name="notifyURL" id="notifyURL" type="text" class="check_required"
                                               value="http://wanglu654321.wicp.net/test/pay/notify" placeholder="必填"/></p>

            <p><label>商户退款订单流水号:</label><input name="merRefundOrderFlowNo" id="merRefundOrderFlowNo" type="text" class="check_required" placeholder="必填"/></p>
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