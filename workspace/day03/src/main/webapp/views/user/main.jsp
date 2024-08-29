<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<title>main</title>
	</head>
	<body>
		${sessionScope.loginUser}님 환영합니다.
		<input type="button" value="로그아웃" onclick="location.replace('/user/logout')">
	</body>
</html>