<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/base/base.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>连接跳转</title>
    <script src="${ctx}/scripts/jquery-3.0.0.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //三秒后跳转
            setTimeout(function(){
                $("#fromId").submit();
            },1000);
        })
    </script>
</head>
<body>
<div class="quickAPI">
    <form id="fromId" method="post" action="${regUrl}">
        <input type="hidden" name="merchantId" value="${merchantId}">
        <input type="hidden" name="tranData" value="${tranData}">
        <input type="hidden" name="merSignMsg" value="${merSignMsg}">
    </form>
</div>
页面将在1秒后跳转......
</body>
</html>