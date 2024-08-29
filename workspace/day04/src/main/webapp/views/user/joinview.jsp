<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<title>joinview</title>
</head>
<body>
	<form name="joinForm" action="joinOk" method="post">
		<fieldset>
			<legend>회원가입</legend>
			<p>
				<input name="userid" placeholder="아이디를 입력하세요!">
				<input type="button" value="중복체크">
			</p>
			<p>
				<input type="password" name="userpw" placeholder="비밀번호를 입력하세요!">
			</p>
			<p>
				<input name="username" placeholder="이름을 입력하세요!">
			</p>
			<p>
				<input type="submit" value="회원가입">
			</p>
		</fieldset>
	</form>
</body>
</html>