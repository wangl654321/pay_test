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
            height: 1250px;
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
        <form id="fromId" method="post" action="${ctx}/ent/saveCorApiUserRegister">
            <h3>企业注册接口 请求参数</h3>
            <p><label>测试地址:</label>

            <input name="regUrl" id="regUrl" type="text" value="${test}/ent/corApiUserRegister.do" placeholder="必填"/></p>

            <p><label>商户代码:</label><input name="merchantId" id="merchantId" type="text" class="check_required" value="${yMerchantId}" placeholder="必填"/></p>
            <p><label>登陆邮箱:</label><input name="userEmail" id="userEmail" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>企业名称:</label><input name="company" id="company" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>是否三证合一:</label>
                <select name="certFlg" id="certFlg">
                    <option value="1">三证合一</option>
                    <option value="2">非三证合一</option>
                </select>
            </p>

            <p><label>营业执照号:</label><input name="certNo" id="certNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>税务登记证:</label><input name="taxregistration" id="taxregistration" type="text" class="check_required" placeholder="必填(非三证合一必传)"/></p>
            <p><label>组织机构代码:</label><input name="organizationCode" id="organizationCode" type="text" class="check_required" placeholder="必填(非三证合一必传)"/></p>

            <p><label>营业执照所在地:</label><input name="certNoAddress" id="certNoAddress" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>营业执照有效期:</label><input name="certExpireDate" id="certExpireDate" type="text" class="check_required" placeholder="必填(yyyymmdd)"/></p>
            <p><label>法定代表人:</label><input name="representative" id="representative" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>法人身份证号:</label><input name="linkmanNo" id="linkmanNo" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>法人证件有效期:</label><input name="linkmanDate" id="linkmanDate" type="text" class="check_required" placeholder="必填(日期类型yyyymmdd)"/></p>

            <p><label>营业执照照片:</label><input name="qyCardImg" id="qyCardImg" type="text" value="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554119579005&di=5d4383fa144b136764b5635fe1b18d65&imgtype=0&src=http%3A%2F%2Fwww.ms.gov.cn%2F__local%2F9%2FB8%2F9C%2F7163346FBE84915FD16ABBFDE2F_45F52755_66F36.png" class="check_required" placeholder="必填"/></p>
            <p><label>法人身份证正面照:</label><input name="frCardImgZ" id="frCardImgZ" type="text" value="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3830200018,593871402&fm=26&gp=0.jpg" class="check_required" placeholder="必填"/></p>

            <p><label>法人身份证反面照:</label><input name="frCardImgF" id="frCardImgF" type="text" value="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554119552010&di=d5ca0762f309693c8718b3025c23dfc8&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180722%2F192c7171eeb240718cd85d9f7298793c.jpeg" class="check_required" placeholder="必填"/></p>
            <p><label>实际控制人:</label><input name="actualController" id="actualController" type="text" class="check_required" placeholder="必填(可以为法人或股东)"/></p>

            <p><label>实际控制人证件类型:</label>
            <select name="controllerCardType" id="controllerCardType">
                    <option value="身份证">身份证</option>
                    <option value="营业执照">营业执照</option>
                    <option value="统一社会信用代码证">统一社会信用代码证</option>
                </select>
            </p>
            <p><label>实际控制人证件号码:</label><input name="controllerCardNo" id="controllerCardNo" type="text" class="check_required" placeholder="必填(可以为法人或股东)"/></p>
            <p><label>企业规模:</label>
                <select name="qscale" id="qscale">
                    <option value="1">500人以上</option>
                    <option value="2">200-500人</option>
                    <option value="3">10-200人</option>
                    <option value="4">10人及以下</option>
                </select>
            </p>

            <p><label>行业类别:</label>
                <select name="ctvc" id="ctvc">
                    <option value="1A">各类专业，技术人员</option>
                    <option value="1B">国家机关，党群组织，企事业单位负责人</option>
                    <option value="1C">办事人员和有关人员</option>
                    <option value="1D">商业工作人员</option>

                    <option value="1E">服务性工作人员</option>
                    <option value="1F">农林牧渔劳动者</option>
                    <option value="1G">生产工作，运输工作和部分体力劳动者</option>
                    <option value="1H">不便分类的其他劳动者</option>
                    <option value="2A">农、林、牧、渔业</option>
                    <option value="2B">采矿业</option>

                    <option value="2C">制造业</option>
                    <option value="2B">采矿业</option>
                    <option value="2D">电力、燃气及水的生产和供应业</option>

                    <option value="2E">建筑业</option>
                    <option value="2F">交通运输、仓储和邮政业</option>

                    <option value="2G">信息传输、计算机服务和软件业</option>
                    <option value="2H">批发和零售业</option>
                    <option value="2I">住宿和餐营业</option>
                    <option value="2J">金融业</option>
                    <option value="2K">房地产业</option>

                    <option value="2L">租赁和商户服务业</option>
                    <option value="2M">科学研究、技术服务和地址勘查业</option>

                    <option value="2N">水利、环境和公共设施管理业</option>
                    <option value="2O">居民服务和其他服务业</option>

                    <option value="2P">教育</option>
                    <option value="2Q">卫生、社会保障和社会福利业</option>
                    <option value="2R">文化、教育和娱乐业</option>
                    <option value="2S">公共管理和社会组织</option>
                    <option value="2T">国际组织</option>
                </select>
            </p>

            <p><label>常用地址:</label><input name="companyAddress" id="companyAddress" type="text" class="check_required" placeholder="必填(企业地址)"/></p>
            <p><label>注册资本:</label><input name="registeredCapital" id="registeredCapital" type="text" placeholder="必填"/></p>
            <p><label>联系人姓名:</label><input name="linkman" id="linkman" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>联系人手机号:</label><input name="phone" id="phone" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>联系人职务:</label><input name="linkjob" id="linkjob" type="text" class="check_required" placeholder="必填"/></p>
            <p><label>联系人固定电话:</label><input name="teleph" id="teleph" type="text" placeholder="非必填"/></p>

            <p><label>传真:</label><input name="fax" id="fax" type="text" placeholder="非必填"/></p>
            <p><label>经营范围:</label><input name="businessScope" id="businessScope" type="text" class="check_required"  placeholder="必填"/></p>
            <p><label>回调地址:</label><input name="notifyUrl" id="notifyUrl" value="http://kang115326.xicp.net:13829/test/register/notify" type="text"class="check_required"  placeholder="非必填"/></p>
            <p><label>受益人:</label><input name="submitContent" id="submitContent" type="text" placeholder="非必填"/></p>
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