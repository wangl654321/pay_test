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

            $.post($("#fromId").attr("action"), $("#fromId").serialize(), function (data) {
                if (data == "") {
                    alert("未知异常");
                    return false;
                }
                alert(data);
            });
        }

        function changeSelect() {
            var cardType = $("#cardType").val();
            if("02" == cardType){
                $("#cvv2").attr("class","check_required");
                $("#expDt").attr("class","check_required");
            }else {
                $("#cvv2").removeAttr("class");
                $("#expDt").removeAttr("class");
            }
        }
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/union/saveUnionQuickPay">
            <h3>银联协议支付 请求参数</h3>
            <p><label>测试地址:</label>

            <input name="regUrl" id="regUrl" type="text" value="${test}/pay/unionQuickPay.do" placeholder="必填"/></p>
            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required"  placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="unionQuickPay"  placeholder="必填"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="YLZF1.0" placeholder="必填"/></p>
            <p><label>交易ip:</label><input name="transIP" id="transIP" type="text" value="127.0.0.1" class="check_required" placeholder="必填"/></p>


            <p><label>cvv2:</label><input name="cvv2" id="cvv2" type="text" placeholder="非必填"/></p>
            <p><label>有效期:</label><input name="expDt" id="expDt" type="text" placeholder="非必填"/></p>

            <p><label>商户订单号:</label><input name="orderNo" id="orderNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>短信验证码:</label><input name="authCode" id="authCode" type="text" class="check_required" placeholder="必填"/></p>

            <p><label>协议号:</label><input name="procotolId" id="procotolId" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>订单金额:</label><input name="orderAmt" id="orderAmt" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>回调地址:</label><input name="notifyUrl" id="notifyUrl" value="http://wanglu654321.wicp.net/test/register/notify" type="text"class="check_required"  placeholder="非必填"/></p>
            <p><label>备用字段:</label><input name="remark" id="remark" type="text" placeholder="非必填"/></p>
            <p><label>商品名称:</label><input name="goodsName" id="goodsName" type="text" placeholder="非必填"/></p>
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