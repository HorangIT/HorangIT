package com.a101.ssafy.project.model;

import io.swagger.annotations.ApiModelProperty;

public class PaginationResponse {
	
    @ApiModelProperty(value = "status", position = 1)
    public boolean status;
    
    @ApiModelProperty(value = "page", position = 2)
    public int page;
    
    @ApiModelProperty(value = "total_elements", position = 3)
    public long total_elements;
    
    @ApiModelProperty(value = "total_pages", position = 4)
    public int total_pages;

    @ApiModelProperty(value = "data", position = 5)
    public String data;
    
    @ApiModelProperty(value = "object", position = 6)
    public Object object; 
    
}
