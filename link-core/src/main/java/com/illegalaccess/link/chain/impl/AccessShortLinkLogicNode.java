package com.illegalaccess.link.chain.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.illegalaccess.link.chain.AccessShortLinkContext;
import com.illegalaccess.link.chain.AccessShortLinkNode;
import com.illegalaccess.link.core.business.ShortLinkBusiness;
import com.illegalaccess.link.core.dto.RedirectShortLinkResp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccessShortLinkLogicNode extends AccessShortLinkNode {

	@Autowired
	private ShortLinkBusiness shortLinkBusiness;
	
	@Override
	public int order() {
		return 1;
	}

	@Override
	public boolean doExecute(AccessShortLinkContext context) throws Exception {
		log.info("AccessShortLinkLogicNode will run");
		RedirectShortLinkResp result = shortLinkBusiness.getRedirectLink(context.getShortLinkCode());
		context.setResult(result);
		return true;
	}

}
