package com.illegalaccess.link.chain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public abstract class AccessShortLinkNode implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		AccessShortLinkContext aslc = (AccessShortLinkContext) context;
		return doExecute(aslc);
	}
	
	/**
	 * 执行节点的顺序，值越低，优先级越高
	 * @return
	 */
	public abstract int order();
	
	public abstract boolean doExecute(AccessShortLinkContext context) throws Exception;

}
