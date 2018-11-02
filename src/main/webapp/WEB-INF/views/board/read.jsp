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
	
<script type="text/javascript">

function deleteCheck(boardnum){
	if(confirm("정말 삭제하시겠습니까?")){
		location.href = 'delete?boardnum=' + boardnum;
	}
}

function replyFormCheck() {
	var retext = document.getElementById('retext');
	if (retext.value.length < 5) {
		alert('리플 내용을 입력하세요.');
		retext.focus();
		retext.select();
		return false;
	}
	return true;			
}


function replyEditForm(replynum, boardnum, retext) {
	var div = document.getElementById("div"+replynum);
	
	var str = '<form name="editForm' + replynum + '" action="replyEdit" method="post">';
	str += '<input type="hidden" name="replynum" value="'+replynum+'">';
	str += '<input type="hidden" name="boardnum" value="'+boardnum+'">';
	str += '&nbsp;';
	str += '<input type="text" name="text" value="' + retext + '" style="width:530px;">';
	str += '&nbsp;';
	str += '<a href="javascript:replyEdit(document.editForm' + replynum + ')">[save]</a>';
	str += '&nbsp;';
	str += '<a href="javascript:replyEditCancle(document.getElementById(\'div' + replynum + '\'))">[cancel]</a>';
	str += '</form>';
	div.innerHTML = str;
}

function replyEditCancle(div) {
	div.innerHTML = '';
}

function replyEdit(form) {
	if (confirm('수정된 내용을 저장하시겠습니까?')) {
		form.submit();
	}
}

function replyDelete(replynum, boardnum) {
	if (confirm('리플을 삭제하시겠습니까?')) {
		location.href='replyDelete?replynum=' + replynum + '&boardnum=' + boardnum;
	}
}
</script>

</head>
<body>
<div id="wrapper">
<section id="main">
<h2>PARK</h2>
<p>See how the sun shines brightly in the city</p>


<h2>read</h2>


<section class="thumbnails">

<div id="wrap">
<ul class="icons">
	<li>${board.inputdate }</li>
	<li>${board.id}</li>
	<li>${board.hits}</li>

</ul>
	<img src="/boardfile/${board.originalfile}" width="300">
	<p>${board.content}</p>

</div>
</section>

<div>

<c:if test="${logid == board.id}">
	<a href="javascript:deleteCheck(${board.boardnum})">delete</a>
	<a href="edit?boardnum=${board.boardnum}">modify</a>
</c:if>


<a href="list">list</a>
</div>
<br>

<form id="replyform" action="replyWrite" method="post" onSubmit="return replyFormCheck();">
	<input type="hidden" name="boardnum" value="${board.boardnum}" />
	<input type="text" name="text" id="retext" style="width:500px;" />
	<input type="submit" class="button alt" value="save" />
</form>

<br>

<table class="reply">
<c:forEach var="reply" items="${replylist}">
	<tr>
		<td class="replyid">
			<b>${reply.id}</b>
		</td>
		<td class="replytext">
			${reply.text}
		</td>
		<td class="replybutton">
			<c:if test="${logid == reply.id}">
				[<a href="javascript:replyEditForm(${reply.replynum}, ${reply.boardnum}, '${reply.text}')">modify</a>]
			</c:if>
		</td>
		<td class="replybutton">
			<c:if test="${logid == reply.id}">
				[<a href="javascript:replyDelete(${reply.replynum}, ${reply.boardnum })">delete</a>]
			</c:if>
		</td>
	</tr>	
	<tr>

		<td class="white" colspan="4"><div id="div${reply.replynum}"></div></td>
	</tr>
		 
</c:forEach>
</table>


</section>
</div>
<%@include file="../include/footer.jsp" %>
</body>
</html>
