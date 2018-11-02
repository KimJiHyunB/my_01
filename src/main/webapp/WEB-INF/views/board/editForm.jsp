<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML>
<html>
<head>
<title></title>
<link rel="stylesheet" href="../resources/assets/css/main.css" />

<%@include file="../include/header.jsp" %>

<script>
function formCheck() {
	var title = document.getElementById('title');
	var content = document.getElementById('content');
	
	if (title.value.length < 5) {
		alert("제목을 입력하세요.");
		title.focus();
		title.select();
		return false;
	}
	if (content.value.length < 5) {
		alert("내용을 입력하세요.");
		title.focus();
		title.select();
		return false;
	}
	return true;
}
</script>	
</head>
<body id="top">
<section id="main" class="wrapper style1">
<header class="major">
<h2>PARK</h2>
<p>You had to hide away for so long</p>
</header>
<div class="container">
<section>
<h2>write modify</h2>

<form id="writeform" action="edit"  method="post" 
	enctype="multipart/form-data" onsubmit="return formCheck();">
	<input type="hidden" name="boardnum" value="${board.boardnum }">
	
<table>
<tr>
	<td>title</td>
	<td>
		<input type="text" name="title" id="title" style="width:400px;" value="${board.title}">
	</td>
</tr>
<tr>
	<td>content</td> 
	<td>
		<textarea name="content" id="content" style="width:400px;height:200px;resize:none;">${board.content}</textarea>
	</td>
</tr>
<tr>
	<td>file</td> 
	<td>
		<input type="file" name="upload" size="30">
		${board.originalfile}
	</td>
</tr>
<tr>
	<td colspan="2" class="white center">
		<input type="submit" class="button alt" value="modify">
	</td> 
</tr>
</table>
</form>
</section>

</div>
</section>
<%@include file="../include/footer.jsp" %>
</body>
</html>
