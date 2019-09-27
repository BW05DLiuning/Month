
package com.liuning.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.liuning.entity.User;
import com.liuning.utils.ConstantFinal;


public class CmsInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(	request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY)==null) {
			response.sendRedirect("/user/login");//如果要去获取session作用域中的用户 结果不存在就去返回到登录页面
			return false;
		}
	String requestURI = request.getRequestURI();
	//request.getRequestURL() http://localhost:8080/jqueryLearn/resources/request.jsp 
	//request.getRequestURL() 返回全路径
	User user=(User)request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
	if(!"1".equals(user.getRole())&& requestURI.matches("^/admin/\\w*")) {
		//判断这个用户是否是管理员  然后路径后面的正则表达式
		request.setAttribute("error", "您不是管理员！请勿作死");//提示
		request.getRequestDispatcher("/user/login").forward(request, response);//跳转登录页面
		return false;
	}
	
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
