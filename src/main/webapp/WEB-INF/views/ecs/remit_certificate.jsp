<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/base/base.jsp" %>
<html>
<head>
    <title>模拟商户页面</title>
    <link rel='stylesheet' href="${ctx}/css/base.css"/>
    <link rel='stylesheet' href="${ctx}/css/index.css"/>
    <script src="${ctx}/scripts/jquery-3.0.0.min.js"></script>

    <link href="${ctx}/select/select2.css" rel="stylesheet"/>
    <script src="${ctx}/select/select2.js"></script>

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
            $("#area").select2();

            $('#area').select2({
                placeholder: '请选择区域'
            });
        });
        $(function () {

        })

        function subFrom() {
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
        <form id="fromId" method="post" action="${ctx}/ecs/toRemitCertificate">
            <h3>汇款凭证 请求参数</h3>

            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/ecs/remitCertificate.do"
                       required="required"/>
            </p>
            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text" value="${yMerchantId}"
                                          class="check_required" placeholder="必填"/></p>

            <p><label>商户流水号:</label><input name="merchantSerial" id="merchantSerial" type="text" placeholder="必填"
                                           required="required"/></p>
            <p><label>汇款单号:</label><input name="orderNo" id="orderNo" type="text" placeholder="协议号"
                                          required="required"/></p>
           <%-- <p>
                <label>汇款单号:</label>
                <select id="area" class="select2">
                    <option value="010">银联商户侧无跳转</option>
                    <option value="011">银联代扣</option>
                    <option value="102">中国工商银行</option>
                    <option value="103">中国农业银行</option>
                    <option value="104">中国银行</option>
                    <option value="105">中国建设银行</option>
                    <option value="201">国家开发银行</option>
                    <option value="202">中国进出口银行</option>
                    <option value="301">交通银行</option>
                </select>
            </p>--%>

            <p>
                <input type='submit' value='提 交'/>
                <input type='button' value='返 回' onclick="javascript:history.go(-1)"/>
            </p>
        </form>
    </div>

</div>
</body>
<jsp:include page="/base/tail.jsp"/>
</html>