package com.a101.ssafy.project.model.chat;

/**
 * @author 송은주(OctopusSwellfish)
 * 
 * ChatMessage의 Type이 어떤 것인지를 표현해줄 수 있는 열거형입니다.
 * 
 */
public enum MessageType {
	CHAT, //채팅일 경우
	JOIN, //채팅에 들어왔을 경우
	LEAVE, //채팅에서 나갔을 경우
	FLEX, //FLEX버튼을 눌렀을 경우
	AUCTION, //AUCTION(응찰)버튼을 눌렀을 경우
	REPLY //BackEnd단에서 답변해주는 경우
}
