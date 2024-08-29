<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<title>ajaxTest</title>
	</head>
	<body>
		<p id="result"></p>
		<input type="button" value="통신하기" onclick="f()">
	</body>
<script>
	function f(){
		const xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			//                           4 와 같음
			if(xhr.readyState == XMLHttpRequest.DONE){
				console.log(xhr.responseText);
				
				document.getElementById("result").innerHTML = xhr.responseText;
			}
		}
		
		xhr.open("GET","/ajax/test");
		xhr.send();
	}
</script>
</html>