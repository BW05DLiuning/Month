package com.liuning.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Update;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.druid.sql.PagerUtils;
import com.github.pagehelper.PageInfo;
import com.liuning.entity.Article;
import com.liuning.entity.Category;
import com.liuning.entity.Channel;
import com.liuning.entity.Comment;
import com.liuning.entity.User;
import com.liuning.service.ArticleService;
import com.liuning.service.CategoryService;
import com.liuning.service.ChannelService;
import com.liuning.utils.ConstantFinal;
import com.liuning.utils.PageUtil;

/**
 * 
 * @author liuning
 *
 */
@Controller
@RequestMapping("article")
public class ArticleController {
	
	private Logger log = Logger.getLogger(ArticleController.class);
	

	@Autowired
	private ChannelService channelService;

	@Autowired
	private CategoryService catService;

	@Autowired
	ArticleService articleService;
	
	
	@RequestMapping("update")//修改  传入article对象
	public String Update(Article article,HttpServletRequest request) {
		 Article article2 = articleService.Seleartic(article);
		 request.setAttribute("art", article2);
		 request.setAttribute("content1", article2.getContent());
		return "my/article/update";
		
	}
	/**
	 * 
	 * @param request
	 * @param cid
	 *            根据分类的id去查询每个 类别下的文章
	 * @return
	 */
	@RequestMapping("listbyCatId")
	public String getListByCatId(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer channelId,
		    @RequestParam(defaultValue = "0") Integer catId,
		    @RequestParam(value="page",defaultValue = "1") Integer pageNum
		) {
		PageInfo<Article> arPage = articleService.list(pageNum, channelId, catId);
		request.setAttribute("pageInfo", arPage);
	
		System.out.println("啊"+channelId);
		System.out.println("啊"+catId);
		//进行分页
		String pageString = PageUtil.page(arPage.getPageNum(), arPage.getPages(), "/article/listbyCatId?catId="+catId+"&channelId="+channelId, arPage.getPageSize());
		request.setAttribute("pageStr", pageString);
		return "index/article/list";
	}

	@RequestMapping("hots")
	public String hots(HttpServletRequest request,
			 @RequestParam( value="pageSize",defaultValue = "4") Integer pageSize,
			 @RequestParam(value="page",defaultValue = "1") Integer pageNum) {
		PageInfo<Article> arPage = articleService.listhots(pageNum,pageSize);
		request.setAttribute("pageInfo", arPage);
		String pageString = PageUtil.page(arPage.getPageNum(), arPage.getPages(), "/article/hots",arPage.getPageSize());
		request.setAttribute("pageStr", pageString);
		return "index/article/list";
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("toPublish")
	public String toPublish() {

		return "my/article/publish";
	}

	@RequestMapping("publish")
	@ResponseBody
	public boolean publish(HttpServletRequest request,
			@RequestParam("file") MultipartFile img, Article article) 
					throws IllegalStateException, IOException {

		 CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		    // 设置编码
		    commonsMultipartResolver.setDefaultEncoding("utf-8");
		 // 判断是否有文件上传
		if (commonsMultipartResolver.isMultipart(request) && img != null) {
			log.debug("img777  is " + img );
			// 获取原文件的名称
			String oName = img.getOriginalFilename();
			log.debug("oName is " + oName );
			// 得到扩展名
			String suffixName = oName.substring(oName.lastIndexOf('.'));
			// 新的文件名称
			String newName = UUID.randomUUID() + suffixName;
			img.transferTo(new File("D:\\pic\\" + newName));//另存
			article.setPicture(newName);//
		}
		User loginUser = (User)request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
		if(loginUser==null)
			return false;
		article.setUserId(loginUser.getId());
		int result = articleService.add(article);
		return result > 0;
		
	
	}

	/**
	 * 获取所有的频道
	 * 
	 * @return
	 */
	@RequestMapping("getAllChn")
	@ResponseBody
	public List<Channel> getAllChn() {
		List<Channel> channels = channelService.getChannels();
		return channels;
	}

	/**
	 * 获取某个频道下的所有的分类
	 * 
	 * @return
	 */
	@RequestMapping("getCatsByChn")
	@ResponseBody
	public List<Category> getCatsByChn(Integer channelId) {
		List<Category> cats = catService.getCategoryByChId(channelId);
		return cats;
	}

	/**
	 * 
	 * @param request
	 * @param aId
	 *            根据id查看文章内容
	 * @return
	 */
	@RequestMapping("getDetail")//更具分类下文章的id去查看详情
	public String getDetail(HttpServletRequest request, Integer aId,Comment comment,
			 @RequestParam( value="pageSize",defaultValue = "4") Integer pageSize,
			 @RequestParam(value="page",defaultValue = "1") Integer pageNum) {
		List<Comment> selehotArticlelist = articleService.selehotArticle();
		request.setAttribute("selehot", selehotArticlelist);
		
		
		Article article = articleService.findById(aId);
		request.setAttribute("article", article);
		//评论
		int selenumber = articleService.selenumber(aId);
		request.setAttribute("selenumber", selenumber);
		
		//点击
		List<Comment> selehitlist= articleService.selehit();
		request.setAttribute("selehitlist", selehitlist);
		
		
		
		PageInfo<Comment> com = articleService.Findcomment(aId,pageSize,pageNum);//根据文章的id 去查询文章下面的评论
		request.setAttribute("com", com);
		String commentPageIfor = PageUtil.page(com.getPageNum(), com.getPages(), "/article/getDetail?aId="+aId, com.getPageSize());
		request.setAttribute("commentPageIfor", commentPageIfor);
		
		return "index/article/detail";
	}

	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
	
	@GetMapping("listMyArticle")//更具用户的id查询  用户所发表的文章  如果文章过多的话 就去进行分页
	public String listMyArticle(HttpServletRequest request,
			@RequestParam(value="page",defaultValue= "1") int pageNum,
			@RequestParam(defaultValue= "5") int pageSize) {
		
		// 获取当前登陆的用户  
		User currUser = (User)request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
		if(currUser==null)  return "my/article/list";//如果获取session中的用户的时候  获取到的为空的话 就去跳转到主页面 然后去登录再次获取
		
		PageInfo<Article> articlePage = articleService.getByUserId(currUser.getId(),pageNum,pageSize);
		//getByUserId  这个方法是去数据库当中去根据用户id去查询 (作者,发布时间,频道,分类),分页信息传过去在service中进行分页  
		System.out.println("articlePage is "  + articlePage);
		//查询出来的文章数据分页后放入request作用域当中(数据信息)
		request.setAttribute("myarticles", articlePage);
		//分页工具类
		String pageStr = PageUtil.page(articlePage.getPageNum(), articlePage.getPages(), "/article/listMyArticle", articlePage.getPageSize());
		//分页工具  (这个分页工具类只是一个在页面底部显示的一个链接  可以去点击切换)
		request.setAttribute("pageStr", pageStr);
		//跳转到/my-cms/src/main/webapp/WEB-INF/view/my/article/list.jsp
		return "my/article/list";
	}
	
	/**
	 * 
	 * @param request
	 * @param status  文章的状态  0 待审  1 已经审核通过  2 审核未通过
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@GetMapping("checkList")
	public String checkList(HttpServletRequest request,
			@RequestParam(defaultValue="0")  Integer status,
			@RequestParam(defaultValue="1") int pageNumber ,
			@RequestParam(defaultValue="5")  int pageSize) {
		
		PageInfo<Article> articlePage =  articleService.checkList(status,pageNumber,pageSize);
		request.setAttribute("articles", articlePage);
		return "admin/article/checkList";
	}
	@RequestMapping("perpage")
	@ResponseBody
	public Article  perpage(HttpServletRequest request,int id) {
		System.out.println(id);
		Article seleallArticle = articleService.seleallArticle(id);
		
		Integer categoryId = seleallArticle.getCategoryId();
		Date created = seleallArticle.getCreated();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String created2 = dateFormat.format(created);
		
		Article article = articleService.perpage(categoryId,created2);
		
		return article;
	}
	@RequestMapping("nextpage")
	@ResponseBody
	public Article nextPage(HttpServletRequest request,int id) {
		
		Article seleallArticle = articleService.seleallArticle(id);
		
		Integer categoryId = seleallArticle.getCategoryId();
		Date created = seleallArticle.getCreated();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String created2 = dateFormat.format(created);
		
		System.out.println(categoryId);
		System.out.println(created2);
		Article article = articleService.nextpage(categoryId,created2);
		return article;
		
	}
	
	/**
	 * 审核的
	 * @param request
	 * @param id
	 * @return
	 */
	@GetMapping("get")
	public String getCheckDetail(HttpServletRequest request,Integer id) {
		
		Article article = articleService.findById(id);
		request.setAttribute("article", article);
		return "admin/article/detail";
	}
	
	/**
	 *  审核文章  
	 * @param request
	 * @param id  文章的id
	 * @param status  1 通过   2 不通过
	 * @return
	 */
	@PostMapping("pass")
	@ResponseBody
	public boolean pass(HttpServletRequest request,Integer id,Integer status) {
		
		int result = articleService.check(id,status);
		return result>0;
	}
	@RequestMapping("pasc")
	@ResponseBody
	public boolean pasc(HttpServletRequest request,Integer id,Integer hot) {
		int checkhot = articleService.checkhot(id,hot);
		return checkhot>0;
		
	}
	@RequestMapping("MyComment")//前台传过来文章的id 和评论的内容 去添加
	@ResponseBody
	public boolean MyComment(HttpServletRequest request,Comment comment) {
		User user = (User)request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
		
			comment.setUserid(user.getId());
			
			 boolean i = articleService.pubretext(comment); 
			return i;
		
		
		
		
	}
	
	/*
	 * @RequestMapping("content")
	 * 
	 * @ResponseBody public boolean content(Comment comment,HttpServletRequest
	 * request) { User user = (User)
	 * request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY); Integer
	 * userid = user.getId(); comment.setUserid(userid); boolean i =
	 * articleService.pubretext(comment); return i; }
	 */

	/*
	 * @RequestMapping("selecontent") public String selecontent(Comment
	 * comment,HttpServletRequest request,Integer articleid) { User user = (User)
	 * request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY); Integer
	 * userid = user.getId(); comment.setUserid(userid);
	 * comment.setArticleid(articleid); articleService.selecontent(articleid);
	 * return null;
	 * 
	 * }
	 */
	/*
	 * @RequestMapping("MyComments") public String MyComments(HttpServletRequest
	 * request,@RequestParam(value = "page",defaultValue = "1")int pageNum,
	 * 
	 * @RequestParam(defaultValue = "4")int pageSize){ User user = (User)
	 * request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
	 * if(user==null) return "redirect:/user/login";
	 * 
	 * PageInfo<Comment> myCommentslist =
	 * articleService.MyComments(user.getId(),pageNum,pageSize);
	 * 
	 * request.setAttribute("comlist", myCommentslist); String MyCommentsPage =
	 * PageUtil.page(myCommentslist.getPageNum(), myCommentslist.getPages(),
	 * "/article/MyComments", myCommentslist.getPageSize());
	 * request.setAttribute("mycomm", MyCommentsPage); return "my/comment/list"; }
	 */
	/*
	 * @RequestMapping("dele")
	 * 
	 * @ResponseBody public String delecomment(Integer id,HttpServletRequest
	 * request){ User user = (User)
	 * request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
	 * 
	 * if(user==null) { return "false"; }
	 * 
	 * int reurlt = articleService.delecomment(user.getId(),id);
	 * System.out.println(reurlt+"你好"); if(reurlt!=0) { return "success"; }else {
	 * return "错误"; }
	 * 
	 * 
	 * }
	 */
}
