package com.illegalaccess.link.db.service;

import com.illegalaccess.link.db.dao.ShortLinkDao;
import com.illegalaccess.link.db.entity.ShortLinkEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShortLinkService {

    @Autowired
    private ShortLinkDao shortLinkDao;
    
    public boolean saveAll(List<ShortLinkEntity> list) {
        int rows = shortLinkDao.batchInsert(list);
        return rows > 0;
    }
    
    public ShortLinkEntity queryByShortLink(String shortLink) {
        return shortLinkDao.queryByShortLink(shortLink, LocalDateTime.now());
    }
    
    
}
