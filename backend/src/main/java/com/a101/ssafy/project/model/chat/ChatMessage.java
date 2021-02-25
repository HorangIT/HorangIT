package com.a101.ssafy.project.model.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 송은주(OctopusSwellfish)
 * 채팅 메시지를 받고 보낼 때 쓰는 DTO입니다.
 * 
 * 메시지가 어떤 타입인지, 내용이 무엇인지, 누가 보냈는지의 정보를 담습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessage {
	private MessageType type;
	private Object content;
	private String sender;
}
