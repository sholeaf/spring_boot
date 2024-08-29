<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>getview</title>
</head>

<body>
			<p>
				제목  <input  name="boardtitle" value="${board.boardtitle}" readonly>
			</p>
			<p>
				작성자 <input type="text" name="userid" value="${board.userid}" readonly>
			</p>
			<p>
				작성일자 ${board.regdate}
			</p>
			<p>
				내용 <textarea name="boardcontents" readonly>${board.boardcontents}</textarea>
			</p>
			
			<div id="btn_area">
				<a href="/user/main">목록</a>
				<c:if test="${board.userid == loginUser}">
					<!--수정 버튼 클릭시 수정 페이지로 이동, 그 곳에서 수정 완료 클릭하면 다시 getview로 이동-->
					<c:if test="${board.boardnum != null}">
						<a href="/board/update?boardnum=${board.boardnum}">수정</a>
					</c:if>
					<!--삭제버튼 클릭시 게시글 삭제 후 main페이지로 이동-->
					<a href="/board/delete?boardnum=${board.boardnum}">삭제</a>
				</c:if>
			</div>
</body>
<script>
	
</script>
</html>