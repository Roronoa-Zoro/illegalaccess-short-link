package com.illegalaccess.link.chain;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.chain.impl.ContextBase;

import lombok.Builder;
import lombok.Data;

@Data
public class AccessShortLinkContext<T> extends ContextBase {
	
	private String shortLinkCode;
	private HttpServletRequest httpServletRequest;
	
	private T result;

}
