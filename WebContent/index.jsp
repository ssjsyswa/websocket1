<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>webchat</title>
<!-- <script src="/jquery.js"></script> -->


</head>
<body>
	<input type="text" name="tx" id="tx">
	</br>
	<div>
		<button onclick="send()">提交</button>
	</div>
	<div id="message"></div>
</body>

<script type="text/javascript">
var websocket = null;
     //判断当前浏览器是否支持WebSocket
     if ('WebSocket' in window) {
        websocket = new WebSocket("ws://127.0.0.1:8080/web/websocket");
        }    
     else {
        alert('当前浏览器 Not support websocket')
     }
	websocket.onerror=function(data){
		document.getElementById('message').innerHTML="连接失败";
	}
	websocket.onopen=function(){
		document.getElementById('message').innerHTML="连接";
	}
	websocket.onmessage=function(data){
		document.getElementById('message').innerHTML+=data.data+"</br>";
		
	}
	websocket.onclose=function(){
		document.getElementById('message').innerHTML="关闭连接";
	}
	window.onbeforeunload=function(){
		websocket.close();
	}
	function send(){
		var message = document.getElementById('tx').value;
		websocket.send(message);
	}
	</script>
</html>