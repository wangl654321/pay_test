<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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

        .main {
            width: 1000px;
            margin: 0 auto;
            background: #C0DFFF;
            height: 718px;
        }
        {
            width: 120px;
            height: 35px;
            background: #4287f5;
            color: white;
            margin-left: 70px;
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
    </script>
</head>
<body>
<jsp:include page="/base/head.jsp"/>
<div class="main">
    <div class="quickAPI">
        <form id="fromId" method="post" action="${ctx}/withholding/saveWithholdSign" >
            <h3>代扣签约 请求参数</h3>

            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/pay/withholdSign.do" required="required"/>
            </p>
            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required"  placeholder="必填"/></p>
            <p><label>接口名称:</label><input name="interfaceName" id="interfaceName" type="text" value="withholdSign" required="required"/></p>
            <p><label>版本号:</label><input name="version" id="version" type="text" value="B2C1.0" placeholder="必填"/></p>

            <p><label>签约协议号:</label><input name="orderNo" id="orderNo" type="text" placeholder="协议号" required="required"/></p>

            <p><label>银行卡号:</label><input name="bankAccountNo" id="bankAccountNo" type="text" placeholder="银行卡号" required="required"/></p>

            <p><label>商户返回URL:</label><input name="returnURL" id="returnURL" type="text" class="check_required" value="https://www.baidu.com/" placeholder="必填"/></p>
            <p><label>回调地址:</label><input name="backUrl" id="backUrl" value="http://kang115326.xicp.net:13829/test/pay/notify" type="text"class="check_required"  placeholder="非必填"/></p>
            <p><label>手机号:</label><input name="mobileNo" id="mobileNo" type="text" placeholder="必填" required="required"/></p>
            <p><label>账户名:</label><input name="userName" id="userName" type="text" placeholder="必填" required="required"/></p>
            <p><label>证件类型:</label>
                <select name="certType" id = "certType" required = required>
                    <option value="01">身份证</option>
                </select>
            </p>
            <p><label>证件号码:</label><input name="certNo" id="certNo" type="text" placeholder="必填" required="required"/></p>
            <p><label>信用卡的cvn2:</label><input name="cvn2" id="cvn2" type="text" placeholder="非必填"/></p>
            <p><label>信用卡有效期:</label><input name="expired" id="expired" type="text" placeholder="非必填"/></p>

            <p><label>委托关系期限:</label><input name="validMonth" id="validMonth" value="999" type="text" placeholder="必填(单位为月份，委托关系限期最多为999)" required="required"/></p>
            <p><label>代扣频率:</label><input name="frequency" id="frequency" value="00" type="text" placeholder="必填(商户和持卡人约定的单位时间内发起代扣交易的最大次数)" required="required"/></p>


            <p><label>代扣款项:</label>
                <select name="billType" id = "billType" required = required>
                    <option value="01">移动电话</option>
                    <option value="02">固定电话</option>
                    <option value="03">水费</option>
                    <option value="04">电费</option>

                    <option value="05">煤气费</option>
                    <option value="06">社保</option>
                    <option value="07">小灵通</option>
                    <option value="08">信用卡还款</option>
                </select>
            </p>
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