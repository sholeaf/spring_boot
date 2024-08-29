<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<html>
<head>
	<title>loginview</title>
</head>
<script>
	window.onload = function(){
		if('${cookie.joinid.value}' == ''){
			document.loginForm.userid.focus();
		}
		else{
			alert('${cookie.joinid.value}님 가입을 환영합니다!');
			document.loginForm.userid.value = '${cookie.joinid.value}'
			document.loginForm.userpw.focus();
		}
	}
</script>
<body>
	<form name="loginForm" action="/user/loginOk" method="post">
		<input name="userid" placeholder="아이디를 입력하세요!">
		<input type="password" name="userpw" placeholder="비밀번호를 입력하세요!">
		<input type="submit" value="로그인">
	</form>
</body>
</html>

