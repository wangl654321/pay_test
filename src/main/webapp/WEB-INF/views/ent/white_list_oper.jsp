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
                if (value == '' || value == null) {
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
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/ent/saveWhiteListOper">

            <h3>会员白名单申请/撤销 请求参数</h3>
            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/ent/whiteListOper.do" class="check_required" placeholder="必填"/>
            </p>
            <p><label>商户代码:</label>
                <input name="merchantId" id="merchantId" type="text" class="check_required" value="${yMerchantId}" placeholder="必填"/>
            </p>
            <p><label>会员客户号:</label>
                <input name="customerId" id="customerId" type="text" class="check_required" placeholder="必填"/>
            </p>
            <p><label>操作类型:</label>
                <select name="operType" id="operType">
                    <option value="0">撤销</option>
                    <option value="1">加入</option>
                </select>
            </p>
            <p><label>协议图片地址:</label>
                <input name="whiteProtocol" id="whiteProtocol" type="text" class="check_required" placeholder="必填"
                       value="http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg"/>
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