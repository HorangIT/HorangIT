'use strict';

var stompClient = null;

var testForm = document.querySelector('#testForm');

//function connect(event){
//	
//	var socket = new SockJS('/ws');
//	stompClient = Stomp.over(socket);
//	
//	stompClient.connect({}, onConnected, onError);
//}
//
//
//function onConnected(){
//	// SubScribe to the Public Topic
//	stompClient.subscribe('/topic/public', onMessageReceived);
//	console.log("연결이 됐다......");
//}

function onError(error) {
	console.log("오류났어요!!");
}

function sendMessage(event){
	console.log("클릭했을때 발동??")
	if(stompClient){
		var chatMessage = {
				sender: "이얏호응",
				content: "응찰시도",
				type: 'CHAT'
		};
		
		stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
		
	}
	event.preventDefault();
}

function onMessageReceived(payload){
	var message = JSON.parse(payload.body);
	
	console.log(pID+"pid입니다...");
	
	var pID = document.querySelector("#testID");
	
	pID.value = message.content;
	
}

testForm.addEventListener('submit', sendMessage, true);
