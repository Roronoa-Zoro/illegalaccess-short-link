package com.illegalaccess.link.chain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.chain.impl.ChainBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.illegalaccess.link.core.dto.RedirectShortLinkResp;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class AccessShortLinkChain extends ChainBase {
	
	@Autowired
	private List<AccessShortLinkNode> nodes = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		Collections.sort(nodes, Comparator.comparing(AccessShortLinkNode::order));
		nodes.forEach(node -> {
			super.addCommand(node);
		});
	}
	
	/**
	 * 访问短链接
	 * @param request
	 * @param shortLinkKey
	 * @return
	 * @throws Exception
	 */
	public RedirectShortLinkResp accessShortLink(HttpServletRequest request, String shortLinkKey) throws Exception {
		AccessShortLinkContext<RedirectShortLinkResp> context = new AccessShortLinkContext<>();
		context.setHttpServletRequest(request);
		context.setShortLinkCode(shortLinkKey);
		this.execute(context);
		log.info("access short link successfully");
		return context.getResult();
	}

}
