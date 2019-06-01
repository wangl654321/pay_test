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
        function subFrom() {
            var flag = false;
            $(".check_required").each(function () {
                var id = this.id
                var value = $("#" + id).val();
                if (value == '') {
                    alert(id + "为空")
                    flag = true;
                    return false
                }
            });
            if (flag) {
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

        function onClear() {
            $.post("${ctx}/toYoYi/clear", function (data) {
                if (data == "") {
                    alert("未知异常");
                    return false;
                }
                if(data == "1"){
                    alert("清除成功");
                }

            });
        }
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/toYoYi/toBanksForPay">

            <h3>获取付款通道 请求参数</h3>
            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/pay/getBanksForPay.do" class="check_required" placeholder="必填"/>
            </p>

            <p><label>商户代码:</label>
                <input name="merchantId" id="merchantId" type="text" class="check_required" value="${yMerchantId}" placeholder="必填"/>
            </p>
            <p><label>接口名称:</label>
                <input name="interfaceName" id="interfaceName" type="text" class="check_required" value="getBanksForPay" placeholder="必填"/>
            </p>
            <p><label>版本号:</label>
                <input name="version" id="version" type="text" value="B2C1.0" class="check_required" placeholder="必填"/>
            </p>

            <p><label>备用字段:</label>
                <input name="remark" id="remark" type="text" placeholder="非必填"/>
            </p>

            <p>
                <input type='button' value='提 交' onclick="subFrom()"/>
                <input type='button' value='返 回' onclick="javascript:history.go(-1)"/>
            </p>
        </form>
        <button onclick="onClear()"> 清 空</button>
    </div>

</div>
</body>
<jsp:include page="/base/tail.jsp"/>
</html>