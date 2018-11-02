<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
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
<body>
<div id="wrapper">
<section id="main">
<h2>welcome park</h2>
<p>You had to hide away for so long</p>


<h1>write</h1>

<form id="writeform" action="write"  method="post" 
	enctype="multipart/form-data" onsubmit="return formCheck();">
	
<table>
<tr>
	<th>title</th>
	<td>
		<input type="text" name="title" id="title" style="width:400px;">
	</td>
</tr>
<tr>
	<th>content</th> 
	<td>
		<textarea name="content" id="content" style="width:400px;height:200px;resize:none;"></textarea>
	</td>
</tr>
<tr>
	<th>file</th> 
	<td>
		<input type="file" name="upload" size="30">
	</td>
</tr>
<tr>
	<td colspan="2" class="white center">
		<input type="submit" value="save" />
	</td> 
</tr>
</table>
</form>
</section>
</div>
</body>
</html>
