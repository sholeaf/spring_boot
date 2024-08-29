<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<title>update</title>
</head>

<body>
	<form name="updateForm" action="/board/updateOk?boardnum=${board.boardnum}" method="post">
			<legend>게시글 수정</legend>
			<p>
				제목 : <input name="boardtitle" value="${board.boardtitle}">
			</p>
			<p>
				작성자 <input type="text" name="userid" value="${sessionScope.loginUser}" readonly>
			</p>
			<p>
				작성일자 ${board.regdate}
			</p>
			<p>
				내용 <textarea name="boardcontents">${board.boardcontents}</textarea>
			</p>
			<p>
				<input type="submit" value="게시글 수정" >
			</p>
			<!--
				a태그로 제출
				<a href="javascript:document.modifyForm.submit()>게시글 수정</a>"
				form 에 action 은 "board/updateOk로 변경"
				hidden으로 name = "boardnum" input 에 사용
			-->
	</form>
</body>
<script>
	
</script>
</html>