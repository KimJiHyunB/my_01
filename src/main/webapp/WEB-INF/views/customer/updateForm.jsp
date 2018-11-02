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
	var pw = document.getElementById('password');
	var pw2 = document.getElementById('password2');
	var name = document.getElementById('name');
	
	if (pw.value.length < 3 || pw.value.length > 10) {
		alert("비밀번호는 3~10자로 입력하세요.");
		pw.focus();
		pw.select();
		return false;
	} 
	if (pw.value != pw2.value) {
		alert("비밀번호를 정확하게 입력하세요.");
		pw.focus();
		pw.select();
		return false;
	}
	if (name.value == '') {
		alert("이름을 입력하세요.");
		name.focus();
		name.select();
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
<p>And don't you know, it's a beautiful new day</p>


<h2>MY PAGE</h2>

<form id="updateform" action="update"  method="post" onsubmit="return formCheck();">
<table>
	<tr>
		<th>ID</th>
		<td>${customer.custid}</td>
	</tr>
	<tr>
		<th>PASSWORD</th>
		<td><input type="password" name="password" id="password" placeholder="password" /><br>
		<input type="password" id="password2" placeholder="password" /></td>
	</tr>
	<tr>
		<th>NAME</th>
		<td><input type="text" name="name" id="name" placeholder="name" value="${customer.name}" /></td>
	</tr>
	<tr>
		<th>EMAIL</th>
		<td><input type="text" name="email" id="email" placeholder="emain" value="${customer.email}" /></td>
	</tr>
</table>
	<br>

<input type="submit" class="button alt" value="modify" />
<input type="reset" class="button alt" value="reset" />

</form>
</section>
</div>
<%@include file="../include/footer.jsp" %>
</body>
</html>