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

            var company = $("#company").val();
            var customerId = $("#customerId").val();
            if(company == "" && customerId == ""){
                alert("会员客户号和公司名称不能同时为空");
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
        <form id="fromId" method="post" action="${ctx}/ent/saveBindBankAcc">

            <h3>企业账户绑定 请求参数</h3>
            <p><label>测试地址:</label>
                <input name="regUrl" id="regUrl" type="text" value="${test}/ent/bindBankAcc.do" class="check_required" placeholder="必填"/>
            </p>
            <p><label>商户代码:</label>
                <input name="merchantId" id="merchantId" type="text" class="check_required" value="${yMerchantId}" placeholder="必填"/>
            </p>
            <p><label>会员客户号:</label>
                <input name="customerId" id="customerId" type="text" class="check_required" placeholder="必填"/>
            </p>
            <p><label>银行帐号:</label>
                <input name="accountNo" id="accountNo" type="text" class="check_required" placeholder="必填(企业在银行开设的对公账户)"/>
            </p>

            <p><label>开户行联行号:</label>
                <select name="unionBankNo" id="unionBankNo">
                    <option value="323331000001">浙江网商银行股份有限公司</option>
                    <option value="320611000067">广西上林国民村镇银行</option>
                    <option value="320633000027">防城港防城国民村镇银行有限责任公司</option>
                    <option value="320631500019">广西浦北国民村镇银行有限责任公司</option>
                    <option value="313662000015">遂宁银行</option>
                    <option value="313657092617">泸州市商业银行</option>
                    <option value="323653010015">重庆富民银行股份有限公司</option>

                    <option value="313504000010">漯河市商业银行</option>
                    <option value="313656000019">攀枝花市商业银行</option>
                    <option value="313653000013">重庆银行</option>
                    <option value="314653000011">重庆农村商业银行股份有限公司</option>
                    <option value="313121006888">河北银行股份有限公司</option>

                    <option value="313127000013">邯郸市商业银行股份有限公司</option>
                    <option value="313141052422">承德银行股份有限公司</option>
                    <option value="313138000019">张家口银行股份有限公司</option>
                    <option value="313143005157">沧州银行</option>

                    <option value="313658000014">长城华西银行</option>
                    <option value="313659000016">绵阳市商业银行</option>
                    <option value="313521000011">汉口银行资金清算中心</option>
                    <option value="313731010015">富滇银行股份有限公司运营管理部</option>
                    <option value="402731057238">云南省农村信用社联合社</option>
                    <option value="313821001016">兰州银行</option>
                    <option value="313871000007">宁夏银行总行清算中心</option>
                </select>
            </p>

            <p><label>默认标识:</label>
                <select name="settleFlag" id="settleFlag">
                    <option value="0">是</option>
                    <option value="1">否</option>
                </select>
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