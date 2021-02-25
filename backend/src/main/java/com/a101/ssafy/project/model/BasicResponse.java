package com.a101.ssafy.project.model;

import com.google.gson.JsonObject;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 송은주(OctopusSwellfish)
 * 
 * HTTP응답을 할 때, 정보를 담기 위한 함수입니다.
 * 
 * 1. 제대로 리턴 되었는지 안 되었는지의 여부인 status와,
 * 2. 해당 data에 대한 정보(e.g. "데이터 조회에 성공하셨습니다.", "응찰 내역이 없습니다." 등 front에서 출력만 하면 될 수 있는 데이터)
 * 3. 필요하다면 정보를 담을 수 있는 Object type으로 구성되어 있습니다.
 *
 */
public class BasicResponse {
    @ApiModelProperty(value = "status", position = 1)
    public boolean status;
    @ApiModelProperty(value = "data", position = 2)
    public String data;
    @ApiModelProperty(value = "object", position = 3)
    public Object object;
}
