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

            var procotolId = $("#procotolId").val();
            if(procotolId == ""){
                $("#bankId").attr("class","check_required");
                $("#payCardNo").attr("class","check_required");
                $("#certNo").attr("class","check_required");
                $("#userName").attr("class","check_required");
            }else {
                $("#bankId").removeAttr("class");
                $("#payCardNo").removeAttr("class");
                $("#certNo").removeAttr("class");
                $("#userName").removeAttr("class");
            }

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
        <form id="fromId" method="post" action="${ctx}/union/saveProtocolCancel">
            <h3>协议解约 请求参数</h3>

            <p><label>测试地址:</label><input name="regUrl" id="regUrl" type="text" value="${test}/pay/protocolCancel.do" class="check_required"  placeholder="必填"/></p>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text" value="${yMerchantId}" class="check_required" placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="protocolCancel" class="check_required" placeholder="必填"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="YLZF1.0" class="check_required" placeholder="必填"/></p>
            <p><label>交易ip:</label><input name="transIP" id="transIP" type="text" value="127.0.0.1" class="check_required" placeholder="必填"/></p>

            <c:if test="${empty list}">
                <p><label style="color: red">付款通道为空:</label>
                    请先 <a href="${ctx}/toYoYi/getBanksForPay">获取付款通道</a>
                </p>
            </c:if>
            <p><label style="color: red">银行ID:</label>
                <select name="bankId" id="bankId" class="check_required">
                    <option value="">请选择</option>
                    <c:forEach items="${list}" var="listFor">
                        <option id="${listFor.cardType}" value="${listFor.bankID}">${listFor.bankName}</option>
                    </c:forEach>
                </select>
            </p>
            <p><label>协议号:</label><input name="procotolId" id="procotolId" type="text" placeholder="协议号和三要素选填一个"/></p>

            <p><label>卡号:</label><input name="payCardNo" id="payCardNo" type="text" placeholder="非必填"/></p>
            <p><label>证件号码:</label><input name="certNo" id="certNo" type="text" placeholder="非必填"/></p>
            <p><label>姓名:</label><input name="userName" id="userName" type="text" placeholder="非必填"/></p>
            <br><br><br>
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