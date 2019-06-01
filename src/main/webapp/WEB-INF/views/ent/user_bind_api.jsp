<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html>
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

            $("#fromId").submit();
        }
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/ent/saveUserBindApi">

            <h3>授权绑定 请求参数</h3>
            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/ent/userBindApi.do" placeholder="必填" class = "check_required"/>
            </p>
            <p><label>商户代码:</label>
                <input name="merchantId" id="merchantId" type="text" value="${yMerchantId}" placeholder="必填" class = "check_required"/>
            </p>
            <p><label>企业会员客户号:</label>
                <input name="customerId" id="customerId" type="text" placeholder="必填"  class = "check_required"/>
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