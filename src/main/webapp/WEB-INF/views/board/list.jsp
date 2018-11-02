<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<script src="http://code.jquery.com/jquery-2.2.4.js"></script>
<!-- masonry  -->
<script src="https://unpkg.com/masonry-layout@4/dist/masonry.pkgd.js"></script>
<!-- imagesloaded -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.imagesloaded/4.1.0/imagesloaded.pkgd.min.js"></script>

<script type="text/javascript">
console.log("1");
function pagingFormSubmit(currentPage) {
	var form = document.getElementById('pagingForm');
	var page = document.getElementById('page');
	page.value = currentPage;
	form.submit();
}

$(document).ready(function() {

		$('.wrap').append(
				
				'<c:forEach var="board" items="${boardlist}">'+
				'<a href="read?boardnum=${board.boardnum}">'+
				'<img src="/boardfile/${board.originalfile}" width="300px">'+
				'</a>'+
				'</c:forEach>'
				);
	

	$imgs = $('.wrap').imagesLoaded(function(){
		$imgs.masonry({
			itemSelector : 'img', 
			fitWidth : true 
			
		});
		
	});
	
});

</script>

</head>

<body>
<div id="wrapper">
<section id="main">


<h2>l i s t</h2>
<p>Runnin' down the avenue</p>





<div class="wrap"></div>

</section>







total : ${navi.totalRecordsCount}	
<input type="button" value="write" class="button alt write_btn" onClick="location.href='write';">                    

<br><br>

<a href="javascript:pagingFormSubmit(${navi.currentPage - navi.pagePerGroup}">＜＜ </a> &nbsp;&nbsp;
<a href="javascript:pagingFormSubmit(${navi.currentPage - 1})">＜</a> &nbsp;&nbsp;

	<c:forEach var="counter" begin="${navi.startPageGroup}" end="${navi.endPageGroup}"> 
		<c:if test="${counter == navi.currentPage}"><b></c:if>
			<a href="javascript:pagingFormSubmit(${counter})">${counter}</a>&nbsp;
		<c:if test="${counter == navi.currentPage}"></b></c:if>
	</c:forEach>
	&nbsp;&nbsp;
	<a href="javascript:pagingFormSubmit(${navi.currentPage + 1})">＞</a> &nbsp;&nbsp;
	<a href="javascript:pagingFormSubmit(${navi.currentPage + navi.pagePerGroup})">＞＞</a>


<form id="pagingForm" method="get" action="list">
	<input type="hidden" name="page" id="page" />
	제목 : <input type="text"  name="searchText" value="${searchText}" />
	<input type="button" onclick="pagingFormSubmit(1)" value="search">
</form>
 



<%@include file="../include/footer.jsp" %>
</div>
</body>
</html>
