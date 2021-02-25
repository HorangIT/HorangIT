package com.a101.ssafy.project.model.auction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 송은주(OctopusSwellfish)
 * 어떤 유저가 몇번의 아이템에 얼마의 가격으로 응찰을 시도했는지 받아오는 DTO입니다.
 *  
 * This Class is deprecated and is replaced with ChatMessage.
 * 이 클래스는 현재는 쓰이지 않으며, ChatMessage의 구조로 개편되었습니다.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuctionInputDTO {
	private String userId;
	private String itemId;
	private String nowPrice;
}
