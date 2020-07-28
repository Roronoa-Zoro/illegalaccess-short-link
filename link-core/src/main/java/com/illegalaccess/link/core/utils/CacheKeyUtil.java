package com.illegalaccess.link.core.utils;

import com.google.common.base.Joiner;

public class CacheKeyUtil {
	
	private static final String prefix = "illegalaccess.link";
	
	private static final String appPrefix = "appkey";
	private static final String hash = "hash";
	
	private static final String urlPrefix = "url";
	
	public static String createHashAppKey(String appKey) {
		return Joiner.on(CoreConstant.point).join(prefix, appPrefix, hash, appKey);
	}
	
	public static String createAppKey(String appKey) {
		return Joiner.on(CoreConstant.point).join(prefix, appPrefix, appKey);
	}
	
	public static String createLongUrlCacheKey(String longUrl) {
		return Joiner.on(CoreConstant.point).join(prefix, urlPrefix, longUrl);
	}
	
	public static String createShortUrlCacheKey(String shortUrl) {
		return Joiner.on(CoreConstant.point).join(prefix, urlPrefix, shortUrl);
	}

}
