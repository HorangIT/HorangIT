package com.a101.ssafy.project.model.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessage {
	private MessageType type;
	private String content;
	private String sender;
}
