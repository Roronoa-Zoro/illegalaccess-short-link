package com.illegalaccess.link.core.business;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.illegalaccess.link.api.model.ShortLinkResp;
import com.illegalaccess.link.core.ConverterUtil;
import com.illegalaccess.link.core.cache.ShortLinkCache;
import com.illegalaccess.link.core.dto.ShortLinkVO;
import com.illegalaccess.link.db.entity.ShortLinkEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 异步执行的任务
 * @author jimmy
 *
 */
@Slf4j
@Async
@Component
public class AsyncBusiness {

	@Autowired
	private ShortLinkCache shortLinkCache;
	@Autowired
	private ConverterUtil converterUtil;
	 
	/**
	 * 
	 * @param new4Cache 要入缓存的长链接到短链接的映射
	 * @param new4Db    要入缓存的新生成的短链接信息
	 * @return
	 */
	public boolean cacheNewUrls(Set<ShortLinkResp> new4Cache, List<ShortLinkEntity> new4Db) {
		Map<String, String> urls = new4Cache.stream().collect(Collectors.toMap(ShortLinkResp::getLongUrl, ShortLinkResp::getShortUrl));
		shortLinkCache.cacheAllLongUrl(urls);
		log.info("cache all new long url mapping successfully");
		
		List<ShortLinkVO> linkVOList = converterUtil.toLinkVOList(new4Db);
		shortLinkCache.cacheAllShortLinkVO(linkVOList);
		log.info("cache all short url info successfully");
		return true;
	}
}
