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
    </style>
    <script type="text/javascript">
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
        <form id="fromId" method="post" action="${ctx}/settle/savePreprocessQuery">
            <h3>(结算通)发货通知 请求参数</h3>
            <p><label>测试地址:</label>

            <input name="regUrl" id="regUrl" type="text" value="${test}/pay/sendGoodsNotify.do" placeholder="必填"/></p>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required" placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="sendGoods"  placeholder="必填"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/></p>

            <p><label>订单号:</label><input name="orderNo" id="orderNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>交易流水号:</label><input name="transFlowNo" id="transFlowNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>物流公司名称:</label><input name="logisticsName" id="logisticsName" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>物流单号:</label><input name="logisticsNo" id="logisticsNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>物流类型:</label><input name="logisticsType" id="logisticsType" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>发货时间:</label><input name="sendTime" id="sendTime" type="text" class="check_required" placeholder="必填"/></p>
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