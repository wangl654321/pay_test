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

        .main {
            width: 1000px;
            margin: 0 auto;
            background: #C0DFFF;
            height: 1150px;
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
            $(".check_required").each(function () {
                var id = this.id
                var value = $("#" + id).val();
                if (value == '' || value == null) {
                    alert(id + "为空")
                    flag = true;
                    return false;
                }
            });
            if (flag) {
                return false;
            }
            //判断是页面还是接口方式
            var returnFlag = $("#returnFlag").val();
            if ("1" == returnFlag) {
                $("#fromId").attr("action", "${ctx}/toYoYi/saveAnonymousPayOrder/api");
                $.post($("#fromId").attr("action"), $("#fromId").serialize(), function (data) {
                    if (data == "") {
                        alert("未知异常");
                        return false;
                    }
                    alert(data);
                });
            } else {
                //返回页面
                $("#fromId").attr("action", "${ctx}/toYoYi/saveAnonymousPayOrder/jsp");
                $("#fromId").submit();
            }
        }

        //判断返回类型
        function changeDiv() {
            var returnFlag = $("#returnFlag").val();
            //调页面
            if ("0" == returnFlag) {
                $("#regUrl").removeAttr("name");
                $("#interfaceName").removeAttr("name");
                $("#version").removeAttr("name");

                $("#pay_div").attr("style", "display: none")
            } else {
                $("#regUrl").attr("name", "regUrl");
                $("#interfaceName").attr("name", "interfaceName");
                $("#version").attr("name", "version");

                $("#pay_div").removeAttr("style");
            }
        }

        function changeSelect() {
            var mySelect=document.getElementById("bankId");
            var index=mySelect.selectedIndex;
            var options_id = mySelect.options[index].id
            $("#cardType").val(options_id);
        }
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post">

            <h3>接口支付 请求参数</h3>
            <div id="pay_div">
                <p><label>测试地址:</label><input name="regUrl" id="regUrl" type="text" value="${test}/pay/anonymousPayOrder.do" placeholder="必填"/></p>
                <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="anonymousPayOrder" placeholder="必填"/></p>
                <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/>
                </p>
            </div>

            <p><label style="color: red">返回类型:</label>
                <select name="returnFlag" id="returnFlag" onchange="changeDiv()">
                    <option value="1">返回字符串</option>
                    <option value="0">返回页面</option>
                </select>
            </p>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text" value="${yMerchantId}" class="check_required" placeholder="必填"/></p>
            <p><label>订单号:</label><input name="orderNo" id="orderNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>订单金额:</label><input name="orderAmt" id="orderAmt" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>支付币种:</label><input name="curType" id="curType" type="text" value="CNY" class="check_required"  placeholder="必填"/></p>
            <c:if test="${empty list}">
                <p><label style="color: red">付款通道为空:</label>
                    请先 <a href="${ctx}/toYoYi/getBanksForPay">获取付款通道</a>
                </p>
            </c:if>
            <p><label style="color: red">付款通道:</label>
                <select name="bankId" id="bankId" onchange="changeSelect()" class="check_required">
                    <option value="">请选择</option>
                    <c:forEach items="${list}" var="listFor">
                        <option id="${listFor.cardType}" value="${listFor.bankID}">${listFor.bankName}</option>
                    </c:forEach>
                </select>
            </p>


            <p><label>商户返回URL:</label><input name="returnURL" id="returnURL" type="text" class="check_required"
                                             value="https://www.baidu.com/" placeholder="必填"/></p>
            <p><label>商户后台通知URL:</label><input name="notifyURL" id="notifyURL" type="text" class="check_required" value="http://wanglu654321.wicp.net/order/pay/notify" placeholder="必填"/>
            </p>
            <p><label>卡种:</label>
                <select name="cardType" id="cardType">
                    <option value="01">网银支付(借记卡)</option>
                    <option value="02">网银支付(信用卡)</option>
                    <option value="X">网银支付(借记/信用卡)</option>
                </select>
            </p>
            <p><label>备注字段:</label><input name="remark" id="remark" type="text" value="测试" placeholder="非必填"/></p>
            <p><label>商户注册用户号:</label><input name="userId" id="userId" type="text" placeholder="非必填"/></p>

            <p><label>商品类别:</label>
                <select name="goodsType" id="goodsType">
                    <option value="0">实体商品</option>
                    <option value="1">虚拟商品</option>
                </select>
            </p>

            <p><label>是否送卡信息:</label><input name="isBind" id="isBind" type="text" placeholder="非必填"/></p>
            <p><label>手机号:</label><input name="mobile" id="mobile" type="text" placeholder="非必填"/></p>
            <p><label>身份证号:</label><input name="certNo" id="certNo" type="text" placeholder="非必填"/></p>
            <p><label>持卡人姓名:</label><input name="userName" id="userName" type="text" placeholder="非必填"/></p>
            <p><label>银行卡号:</label><input name="cardNo" id="cardNo" type="text" placeholder="非必填"/></p>
            <p><label>银行代码:</label><input name="bankType" id="bankType" type="text" placeholder="非必填"/></p>
            <p>
                <label>商品名称:</label><input name="goodsName" id="goodsName" type="text" value="测试商品" placeholder="非必填"/>
            </p>
            <p>
                <label>扫码支付二级商户号:</label><input name="MSMerchantIdB" id="MSMerchantIdB" type="text" placeholder="非必填"/>
            </p>
            <p><label>二维码有效期:</label><input name="codeTime" id="codeTime" type="text" placeholder="非必填"/></p>
            <p><label>支付授权码:</label><input name="auth_code" id="auth_code" type="text" placeholder="非必填"/></p>
            <p><label>客户端用户真实的ip地址:</label><input name="spbill_create_ip" id="spbill_create_ip" type="text" placeholder="非必填"/></p>
            <br>
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