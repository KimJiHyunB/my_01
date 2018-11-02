<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="../resources/assets/css/main.css" />
<%@include file="../include/header.jsp" %>
</head>
<body>
<div id="wrapper">
<section id="main">


<table>
	<tr>
		<th>id</th>
		<td>${customer.custid}</td>
	</tr>
	<tr>
		<th>password</th>
		<td>${customer.password}</td>
	</tr>
	<tr>
		<th>name</th>
		<td>${customer.name}</td>
	</tr>
	<tr>
		<th>email</th>
		<td>${customer.email}</td>
	</tr>
</table>

<p><a href="../">list</a></p>
</section>
</div>

</body>
</html>