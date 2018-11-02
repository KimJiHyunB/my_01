<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" href="../resources/assets/css/main.css" />

<%@include file="../include/header.jsp" %>

<script>

function formCheck() {
	var id = document.getElementById('id');
	var password = document.getElementById('password');
	
	if (id.value == '' || password.value == '') {
		alert('ID와 비밀번호를 입력하세요.');
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="wrapper">
<section id="main">
<h2>PARK</h2>
<p>It's stopped raining Everybody's in the place</p>



<h2>login</h2>


<form id="loginForm" action="login" method="post" onSubmit="return formCheck();">
<table>
<tr>
	<td>ID</td>
	<td><input type="text" name="custid" id="custid" /></td>
</tr>
<tr>
	<td>PASSWORD</td>
	<td><input type="text" name="password" id="password" /></td>
</tr>
<tr>
	<td></td>
	<td>
		<div class="errorMsg">
			${errorMsg}
		</div>
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" class="button alt" value="Login" />
	</td>
</tr>
</table>
</form>
</section>
</div>


<%@include file="../include/footer.jsp" %>
</body>
</html>