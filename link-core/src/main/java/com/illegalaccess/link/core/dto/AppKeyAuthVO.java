package com.illegalaccess.link.core.dto;

import lombok.Data;

/**
 * appkey auth的值对象
 * @author jimmy
 *
 */
@Data
public class AppKeyAuthVO {
	
	private String appKey;
    
    private String accessMethod;
    
    private Integer status;

}
