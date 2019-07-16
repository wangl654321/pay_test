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
            height: 800px;
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

            var recAcctBankNo = parseInt(Math.random() * 100000);
            recAcctBankNo = 100000000 + recAcctBankNo;
            $("#merchantSerial").val(recAcctBankNo);

            var recAcctBankNo = parseInt(Math.random() * 100000);
            recAcctBankNo =100000000 + recAcctBankNo;
            $("#orderNo").val("P" + recAcctBankNo);

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
        <form id="fromId" method="post" action="${ctx}/single/saveBillOrderUpload" >
            <h3>单笔票据上传 请求参数</h3>

            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/pay/billOrderUpload.do" required="required"/>
            </p>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text"value="${yMerchantId}" class="check_required"  placeholder="必填"/></p>

            <p><label>票据号:</label><input name="billOrder" id="billOrder" type="text" value="" placeholder="必填" class="check_required"/></p>
            <p><label>商户流水号:</label><input name="merchantSerial" id="merchantSerial" type="text" value="" placeholder="必填"/></p>
            <p><label>汇款单号:</label><input name="orderNo" id="orderNo" type="text" placeholder="必填" class="check_required"/></p>

            <p><label>票据正面图片:</label><input name="positive" id="positive" value="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563186775368&di=accce17744f69c375330d7523ec3cd41&imgtype=0&src=http%3A%2F%2Fs8.sinaimg.cn%2Fmw690%2F006sYzV9zy7cdFu2Z9l27%26690" type="text"/></p>

            <p><label>票据反面图片:</label><input name="reverse" id="reverse" value="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563187089925&di=7c7a04b76b0f5d124fea6666f7083ae9&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161123%2F15eb974c7f21444585060bab1d0af461_th.jpeg" type="text"/></p>

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