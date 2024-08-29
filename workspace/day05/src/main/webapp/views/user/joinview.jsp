<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<title>joinview</title>
</head>
<body>
	<form name="joinForm" action="joinOk" method="post">
		<fieldset>
			<legend>회원가입</legend>
			<p id="txt"></p>
			<p>
				<input name="userid" placeholder="아이디를 입력하세요!">
				<input type="button" value="중복체크" onclick="checkId()">
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
<script>
	function checkId(){
		const userid = document.joinForm.userid;
		const txt = document.getElementById("txt");
		
		const xhr = new XMLHttpRequest();
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if(xhr.status == 200){
					let result = xhr.responseText;
					console.log(result);
					if(result == 'O'){
						txt.innerHTML = "사용할 수 있는 아이디입니다.";
					}
					else{
						txt.innerHTML = "중복된 아이디가 있습니다.";
					}
				}
			}				
		}
		
		xhr.open('GET','/user/checkId?userid='+userid.value);
		xhr.send();
	}
</script>
</html>