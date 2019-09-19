package com.liuning.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.liuning.entity.User;
import com.liuning.service.UserService;
import com.liuning.utils.ConstantFinal;
import com.liuning.utils.PageUtil;

/**
 * 
 * @author Administrator
 *
 */
@RequestMapping("user")
@Controller
public class UserController {
	
	/**
	 * 
	 */
	@Autowired
	UserService userService;
	
	/**
	 *  跳转到注册的页面
	 * @return
	 */
	@GetMapping("register")
	public String register() {
		return "user/register";
	}
	
	
	/**
	 * 保存提交注册的数据
	 * @param request
	 * @param user
	 * @return
	 */
	@PostMapping("register")
	public String register(HttpServletRequest request,User user) {
		
		if(null==userService.findByName(user.getUsername())){
			User register = userService.register(user);
			if(register==null) {
				request.setAttribute("error", "注册失败,请与管理员联系");
				return "/user/register";
			}else {
				
				return "redirect:/user/login";
				
			}	
		}else {
			request.setAttribute("error", "注册失败,用户名已经存在");
			return "user/register";
		}
	}
	
	
	/**
	 *  跳转登录的页面
	 * @return
	 */
	@GetMapping("login")
	public String login() {
		return "user/login";
	}
	
	/**
	 *  登录
	 * @param request
	 * @param user
	 * @return
	 */
	@PostMapping("login")
	public String login(HttpServletRequest request,User user) {
		
		User loginUser=  userService.login(user);
				
		if(loginUser==null) {
			request.setAttribute("error", "用户密码错误");
			return "user/login";
		}else {
			request.getSession().setAttribute(ConstantFinal.USER_SESSION_KEY, loginUser);
			return "1".equals(loginUser.getRole())?"redirect:/admin/index" : "redirect:/user/home";	
		}
		
	}
	
	@RequestMapping("home")
	public String home() {
		
		return "my/index";
		
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping("check")
	@ResponseBody
	public boolean checkName(String username) {
		return (null==userService.findByName(username));
	}
	
	
	/**
	 * 
	 * @param request
	 * @param name  模糊查询条件
	 * @return
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,
			@RequestParam( defaultValue="") String name,
			@RequestParam(value="page",defaultValue="1") int pageNumber,
			@RequestParam(defaultValue="3")  int pageSize) {
		
		PageInfo<User> users =  userService.search(pageNumber,pageSize,name);
		String pageStr = PageUtil.page(users.getPageNum(), users.getPages(), "/user/list", users.getPageSize());
		request.setAttribute("pageStr", pageStr);
		request.setAttribute("pageuser", users);
		return "admin/user/list";
		
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute(ConstantFinal.USER_SESSION_KEY);
		return "redirect:/index";
	}
	
	

}
