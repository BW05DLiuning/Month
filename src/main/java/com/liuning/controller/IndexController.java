package com.liuning.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.liuning.entity.Article;
import com.liuning.entity.Channel;
import com.liuning.entity.Yhontio;
import com.liuning.service.ArticleService;
import com.liuning.service.ChannelService;
import com.liuning.utils.PageUtil;

@Controller
public class IndexController {
	
	private Logger log = Logger.getLogger(IndexController.class);
	
	@Autowired
	ChannelService cService;
	@Autowired
	ArticleService articleservice;
	//用于跳转到主页面
	@RequestMapping(value= {"/index","/",""},method=RequestMethod.GET)
	public String index(HttpServletRequest request, @RequestParam( value="pageSize",defaultValue = "4") Integer pageSize,
			 @RequestParam(value="page",defaultValue = "1") Integer pageNum) {
		
		log.info("this is log test");
		//查询所有的频道
		List<Channel> channels = cService.getChannels();
		request.setAttribute("channels", channels);
		//获取热门
		PageInfo<Article> pageInfo = articleservice.listhots(pageSize,pageNum);
		request.setAttribute("pageInfo", pageInfo);
		
		
		List<Article> newarticl = articleservice.newarticl();
		request.setAttribute("lastArticles", newarticl);
		List<Yhontio> yhontio = articleservice.yhontio();
		request.setAttribute("linkList", yhontio);
		
		
		String PageInforIndex = PageUtil.page(pageInfo.getPageNum(), pageInfo.getPages(), "/article/hots", pageInfo.getPageSize());
			request.setAttribute("pageStr", PageInforIndex);
		return "index/index";
	}
	
}
