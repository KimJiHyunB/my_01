<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

<link rel="stylesheet" href="../resources/assets/css/main.css" />
		
<%@include file="../include/header.jsp" %>

<script src="http://code.jquery.com/jquery-2.2.4.js"></script>
<script type="text/javascript">
$(function(){
	$('#checkId').on('click',function(){
		var id = $('#custid').val();

		$.ajax({
			url : "idcheck",
			type : "POST",
			data : {
				custid : id
			},
			success : function(data){
				alert(data);
			},
			error : function(e){
				alert("실패");
			}
		});
	});
});


function formCheck() {
	var id = document.getElementById('custid');
	var pw = document.getElementById('password');
	var pw2 = document.getElementById('password2');
	var name = document.getElementById('name');
	
	if (id.value.length < 3 || id.value.length > 10) {
		alert("ID는 3~10자로 입력하세요.");
		id.focus();
		id.select();
		return false;
	}
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
<h2>welcome park</h2>
<p>Sun is shining in the sky There ain't a cloud in sight</p>


<h1>join us</h1>

<form id="joinform" action="join"  method="post" onsubmit="return formCheck();">
<table>
	<tr>
		<th>ID</th>
		<td>
			<input type="text" name="custid" id="custid" placeholder="id" value="${customer.custid}" />
			<input type="button" value="id check"  class="button alt" id="checkId">
		</td>
	</tr>
	<tr>
		<th>PASSWORD</th>
		<td><input type="password" name="password" id="password" placeholder="password" value="${customer.password}" /><br>
		<input type="password" id="password2" placeholder="password check" value="${customer.password}" /></td>
	</tr>
	<tr>
		<th>NAME</th>
		<td><input type="text" name="name" id="name" placeholder="name"  value="${customer.name}" /></td>
	</tr>
	<tr>
		<th>EMAIL</th>
		<td><input type="text" name="email" id="email" placeholder="email"  value="${customer.email}" /></td>
	</tr>
</table>
	<br>

<input type="submit" class="button alt" value="join" />
<input type="reset"  class="button alt" value="reset" />

</form>
</section>
</div>
</body>
</html>