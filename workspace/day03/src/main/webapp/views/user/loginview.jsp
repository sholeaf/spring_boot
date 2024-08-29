<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>loginview</title>
</head>
<c:if test="${cookie.joinid.value != null && !cookie.joinid.value != ''}">
	<script>
		window.onload = function(){
			alert('${cookie.joinid.value} 님 가입을 환영합니다!');
			
			document.loginForm.userid.value = '${cookie.joinid.value}';
			document.loginForm.userpw.focus();
		}
	</script>
</c:if>
<c:if test="$cookie.joinid.value == null or cookie.joinid.value == ''">
	<script>
		window.onload = function(){
			document.loginForm.userid.focus();
		}
	</script>
	
</c:if>
<body>
	<form name="loginForm" action="loginOk" method="post">
				<input name="userid" placeholder="아이디를 입력하세요!">
				<input type="password" name="userpw" placeholder="비밀번호를 입력하세요!">
				<input type="submit" value="로그인">
	</form>
</body>
</html>