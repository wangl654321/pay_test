<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base/base.jsp" %>
<!DOCTYPE html>
<html>
<link rel='stylesheet' href="${ctx}/css/base.css"/>
<link rel='stylesheet' href="${ctx}/css/index.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API测试首页</title>
<head>
</head>
<body>
<!--  头部 -->
<div class="header">
    <div class="logo">
        <span class="text">接口API测试</span>
    </div>
</div>
<%--<%@ include file="/menu/menu.jsp" %>--%>
<!-- 主页面 -->
<div class="main">
    <ul>
        <li>
            <a href="${ctx}/file/download">对账文件下载</a>
        </li>
        <li>
            <a href="${ctx}/file/save">对账文件解析</a>
        </li>

        <li></li>
        <li></li>

        <li>
            <a href="${ctx}/toYoYi/preprocess">支付请求</a>
        </li>
        <li>
            <a href="${ctx}/toYoYi/preprocess/query">支付请求查询</a>
        </li>
        <li>
            <a href="${ctx}/toYoYi/preprocess/refundOrder">支付退款</a>
        </li>
        <li>
            <a href="${ctx}/toYoYi/preprocess/refundOrderQuery">支付退款查询</a>
        </li>

        <li></li>
        <li></li>

        <li>
            <a href="${ctx}/toYoYi/getBanksForPay">获取付款通道</a>
        </li>
        <li>
            <a href="${ctx}/toYoYi/anonymousPayOrder">接口支付</a>
        </li>
        <li>
            <a href="${ctx}/toYoYi/pay_query/orderQuery">接口支付查询</a>
        </li>

        <li></li>
        <li></li>
        <li></li>

        <li>
            <a href="${ctx}/ent/corApiUserRegister">企业注册接口</a>
        </li>
        <li>
            <a href="${ctx}/ent/corApiQuery">企业信息查询</a>
        </li>
        <li>
            <a href="${ctx}/ent/corApiRegistUpdate">企业信息修改</a>
        </li>
        <li>
            <a href="${ctx}/ent/bindBankAcc">企业账户绑定</a>
        </li>
        <li>
            <a href="${ctx}/ent/bankAccQuery">已绑定银行账户查询</a>
        </li>
        <li>
            <a href="${ctx}/ent/bankAccUnBind">解绑银行账户</a>
        </li>
        <%--<li>
            <a href="${ctx}/ent/corAcctBankAuth">企业账户打款认证(已绑定银行账户)</a>
        </li>--%>

        <li>
            <a href="${ctx}/ent/unCorAcctBankAuth">企业账户打款认证(未绑定银行账户)</a>
        </li>
        <li>
            <a href="${ctx}/ent/toTakeCashApi">提现请求</a>
        </li>

        <li>
            <a href="${ctx}/ent/bankAcctAuthQuery">企业账户打款结果查询</a>
        </li>
        <li>
            <a href="${ctx}/ent/amtAuth">企业账户打款金额认证</a>
        </li>
        <li>
            <a href="${ctx}/ent/userSettingApi">账号设置</a>
        </li>
        <li>
            <a href="${ctx}/ent/userBindApi">授权绑定</a>
        </li>
        <li>
            <a href="${ctx}/ent/toTakeCashApi">提现请求</a>
        </li>
        <li></li>

        <li>
            <a href="${ctx}/ent/whiteListOper">会员白名单申请/撤销</a>
        </li>
        <li>
            <a href="${ctx}/ent/bindWhiteCard">会员白名单卡绑定</a>
        </li>
        <li>
            <a href="${ctx}/ent/unBindWhiteCard">会员白名单卡状态修改</a>
        </li>
        <li>
            <a href="${ctx}/ent/whiteListQuery">白名单会员查询</a>
        </li>

        <li></li>
        <li></li>

        <li>
            <a href="${ctx}/aliPayAndWeChat/anonymousPayOrder">(微信支付宝)支付请求</a>
        </li>
        <li>
            <a href="${ctx}/aliPayAndWeChat/orderQuery">(微信支付宝)退款订单查询</a>
        </li>
        <li>
            <a href="${ctx}/toYoYi/preprocess/refundOrder">(微信支付宝)支付退款</a>
        </li>
        <li>
            <a href="${ctx}/aliPayAndWeChat/orderQuery">(微信支付宝)单笔订单查询</a>
        </li>

        <li></li>
        <li></li>

        <li>
            <a href="${ctx}/union/getMobileCode">发送短信</a>
        </li>
        <li>
            <a href="${ctx}/union/protocolQuery">协议查询</a>
        </li>
        <li>
            <a href="${ctx}/union/protocolCancel">协议解约</a>
        </li>
        <li>
            <a href="${ctx}/union/unionQuickPay">银联协议支付</a>
        </li>
        <li>
            <a href="${ctx}/union/unionQuickRefund">银联退款</a>
        </li>
        <li>
            <a href="${ctx}/union/unionProtocolSign">协议签约</a>
        </li>
        <li>
            <a href="${ctx}/union/unionApiOrderQuery">收单查询</a>
        </li>
        <li>
            <a href="${ctx}/query/merBalanceQuery">商户款余额查询</a>
        </li>

        <li></li>
        <li></li>
        <li>
            <a href="${ctx}/single/pay">单笔汇款接口</a>
        </li>
        <li>
            <a href="${ctx}/single/query">单笔汇款查询接口</a>
        </li>

        <li></li>
        <li></li>

        <li>
            <a href="${ctx}/single/billOrderUpload">单笔票据上传</a>
        </li>

        <li></li>

        <li>
            <a href="${ctx}/settle">结算通测试</a>
        </li>
        <li>
            <a href="${ctx}/protocol">协议支付</a>
        </li>
        <li>
            <a href="${ctx}/withholdSign">代扣</a>
        </li>
        <li>
            <a href="${ctx}/ecs/remitCertificate">汇款凭证</a>
        </li>
    </ul>
</div>
<!--尾部-->
<div class="footer">
    <p>
        <span>关于我们</span>
        <span>合作伙伴</span>
        <span>商务合作</span>
        <span>联系我们</span>
    </p>
    <p class="mark">Copyright © 2011-<%=new SimpleDateFormat("yyyy").format(new Date()) %> 接口测试项目 - Powered By JeeSite V1.0</p>
</div>
</body>
</html>