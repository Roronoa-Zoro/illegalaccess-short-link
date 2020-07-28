package com.illegalaccess.link.chain.impl;

import org.springframework.stereotype.Component;

import com.illegalaccess.link.chain.AccessShortLinkContext;
import com.illegalaccess.link.chain.AccessShortLinkNode;

import lombok.extern.slf4j.Slf4j;

/**
 * 访问短链接的前置检查
 * @author jimmy
 *
 */
@Slf4j
@Component
public class AccessShortLinkCheckNode extends AccessShortLinkNode {

	@Override
	public int order() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean doExecute(AccessShortLinkContext context) throws Exception {
		log.info("AccessShortLinkCheckNode will run");
		return true;
	}

}
