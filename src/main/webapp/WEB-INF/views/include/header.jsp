<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>header</title>

<link rel="stylesheet" href="../resources/assets/css/main.css" />
</head>
<body>

<div id="wrapper">
<header id="header">
<h1>－  p a r k －</h1>
<c:if test="${logid != null}">
	${sessionScope.logid}님 로그인 중<br>
</c:if>

<ul class="icons">
<li><a href="${pageContext.request.contextPath}">PARK</a></li>
<c:if test="${logid == null}">
	<li><a href="${pageContext.request.contextPath}/customer/joinForm">join us</a>
	<li><a href="${pageContext.request.contextPath}/customer/loginForm">login</a>
</c:if>
<c:if test="${logid != null}">
	<li><a href="${pageContext.request.contextPath}/customer/logout">logout</a>
	<li><a href="${pageContext.request.contextPath}/customer/update">my page</a>
</c:if>
	<li><a href="${pageContext.request.contextPath}/board/list">list</a></li>

</ul>
</header>
</div>	
</body>
</html>