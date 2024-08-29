<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<title>ajaxTest</title>
	</head>
	<body>
		<table border="1">
			<tbody>
				<tr>
					<th>실시간 검색 순위</th>
					<th>키워드</th>
				</tr>
				<tr>
					<td id="rank"></td>
					<td id="data"></td>
				</tr>
			</tbody>
		</table>
	</body>
<script>
	let datas = null;
	
	getData();
	setInterval(getData,20000);
	
	setInterval(change,2000);
	
	let i = 0;
	function change(){
		const rank = document.getElementById("rank");
		const data = document.getElementById("data");

		i %= datas.length;
				
		rank.innerHTML = datas[i].rank;
		data.innerHTML = datas[i].data;
		i++;

	}
	
	function getData(){
		const xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if(xhr.status == 200){
					//JSON.parse("JSON 형태의 문자열 데이터") : 안에 적혀있는 뭄ㄴ자열로 자바스크립트 객체화
					//const mycar = JSON.parse('{"model":"Ferrari","color":"Red"}')
					//const my car = {"model":"Ferrari","color":"Red"};
					
					datas = JSON.parse(xhr.responseText).datas;
				}
			}
		}
		xhr.open("GET","datas.json");
		xhr.send();
	}
</script>
</html>