<%@ page import="com.yoyi.pay.controller.BaseUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<%--测试环境--%>
<%--<c:set var="test1" value="http://testpay.yoyipay.cn:28080"/>--%>
<%--正式环境--%>
<%--<c:set var="test" value="https://pay.yoyipay.com"/>--%>
<%--<c:if test="${test.indexOf('https') == -1}">
    <c:set var="yMerchantId" value="M100002746"/>
</c:if>
<c:if test="${test.indexOf('https') != -1}">
    <c:set var="yMerchantId" value="M100001061"/>
</c:if>--%>


<%
    String merchantId = BaseUtils.MERCHANT_ID;
    String url = BaseUtils.URL;
%>
<%--商户号--%>
<c:set var="yMerchantId" value="<%=merchantId%>"/>
<%--请求地址--%>
<c:set var="test" value="<%=url%>"/>