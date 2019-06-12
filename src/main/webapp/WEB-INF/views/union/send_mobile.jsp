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
        <form id="fromId" method="post" action="${ctx}/union/saveMobileCode">
            <h3>发送短信 请求参数</h3>

            <p><label>验证码类型:</label>
                <select name="codeType" id="codeType">
                    <option value="0">签约短信</option>
                    <option value="1">消费短信</option>
                </select>
            </p>

            <c:if test="${empty list}">
                <p><label style="color: red">付款通道为空:</label>
                    请先 <a href="${ctx}/toYoYi/getBanksForPay">获取付款通道</a>
                </p>
            </c:if>
            <p><label style="color: red">付款通道:</label>
                <select name="bankId" id="bankId" class="check_required">
                    <option value="">请选择</option>
                    <c:forEach items="${list}" var="listFor">
                        <option id="${listFor.cardType}" value="${listFor.bankID}">${listFor.bankName}</option>
                    </c:forEach>
                </select>
            </p>

            <p><label>证件类型:</label>
                <select name="certType" id="certType">
                    <option value="1">身份证</option>
                </select>
            </p>

            <p><label>证件号码:</label><input name="certNo" id="certNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>手机号:</label><input name="mobile" id="mobile" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>卡号:</label><input name="cardNo" id="cardNo" type="text" class="check_required" placeholder="必填"/></p>

            <p><label>卡类型:</label>
                <select name="cardType" id="cardType"  onchange="changeSelect()" >
                    <option value="01">网银支付(借记卡)</option>
                    <option value="02">网银支付(信用卡)</option>
                    <option value="X">网银支付(借记/信用卡)</option>
                </select>
            </p>

            <p><label>cvv2:</label><input name="cvv2" id="cvv2" type="text" placeholder="必填(当卡类型是02是为必输)"/></p>
            <p><label>有效期:</label><input name="expDt" id="expDt" type="text" placeholder="必填(当卡类型是02是为必输)"/></p>

            <p><label>姓名:</label><input name="userName" id="userName" type="text" class="check_required" placeholder="必填"/></p>
            <%--<p><label>订单金额:</label><input name="orderAmt" id="orderAmt" type="text" class="check_required" placeholder="必填"/></p>--%>
            <p><label>订单号:</label><input name="orderNo" id="orderNo" type="text" placeholder="非必填"/></p>
            <p><label>协议号:</label><input name="procotolId" id="procotolId" type="text" placeholder="非必填"/></p>
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