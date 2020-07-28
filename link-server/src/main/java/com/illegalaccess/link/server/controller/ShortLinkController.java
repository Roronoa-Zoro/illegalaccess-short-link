package com.illegalaccess.link.server.controller;

import com.illegalaccess.link.api.model.ShortLinkReq;
import com.illegalaccess.link.api.model.ShortLinkResp;
import com.illegalaccess.link.chain.AccessShortLinkChain;
import com.illegalaccess.link.core.business.ShortLinkBusiness;
import com.illegalaccess.link.server.AuthApi;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@Controller
public class ShortLinkController {
	
	@Autowired
	private AccessShortLinkChain accessShortLinkChain;
	@Autowired
	private ShortLinkBusiness shortLinkBusiness;

    @AuthApi(apiMethodName = "generateShortLink")
    @ResponseBody
    @PostMapping("/link/generate")
    public List<ShortLinkResp> generateShortLink(@RequestBody ShortLinkReq req) {
        // todo
    	shortLinkBusiness.generateShortLink(req.getShortLinkDtos());
        return null;
    }

    @RequestMapping("/{shortLinkKey}")
    public RedirectView redirect(HttpServletRequest request, @PathParam("shortLinkKey") String shortLinkKey) {
        // todo
    	try {
			accessShortLinkChain.accessShortLink(request, shortLinkKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
}
