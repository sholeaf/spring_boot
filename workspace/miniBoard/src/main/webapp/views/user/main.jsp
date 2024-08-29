<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>main</title>
</head>
<c:if test="${cookie.w.value == 't'}">
	<script>
		alert("게시글 등록 완료!");
	</script>
</c:if>
<c:if test="${cookie.w.value == 'f'}">
	<script>
		alert("게시글 등록 실패!");
	</script>
</c:if>
<c:if test="${cookie.d.value == 't'}">
	<script>
		alert("삭제 완료!");
	</script>
</c:if>
<c:if test="${cookie.d.value == 'f'}">
	<script>
		alert("삭제 실패!");
	</script>
</c:if>
<body>
	${sessionScope.loginUser}님 환영합니다
	<input type="button" value="로그아웃" onclick="location.replace('/user/logout')">
	<hr>
	<div>
		<!--
			글쓰기 버튼 클릭시 게시판 작성 페이지로 이동
			게시글 제목, 게시글 내용 작성 후 [작성완료] 버튼 클릭
			데이터베이스에 작성된 게시글 데이터 하나 추가
			(게시글 제목 - boardtitle, 게시글 내용 - boardcontents, 작성시간 - regdate, 게시글 작성자 - userid)
		-->
		<input type="button" value="글쓰기" onclick="location.replace('/board/writeview')">
		<hr>
		<table border="">
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="board" items="${list}">
					<!--el문-->
					<tr>
						<td>${board.boardnum}</td>
						<td><a href="/board/getview?boardnum=${board.boardnum}">${board.boardtitle}</td>
						<!--<td><a href="/board/getview.html?boardnum=${board.boardnum}">${board.boardtitle}</td> -->
						<td>${board.userid}</td>
						<td>${board.regdate}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>







