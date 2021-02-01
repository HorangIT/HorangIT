package com.a101.ssafy.project.model.auction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuctionInputDTO {
	private String userId;
	private String itemId;
	private String nowPrice;
}
