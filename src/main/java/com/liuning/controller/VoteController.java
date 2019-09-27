package com.liuning.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.liuning.entity.Article4Vote;
import com.liuning.entity.User;
import com.liuning.entity.VoteStatic;
import com.liuning.service.Article4VoteService;
import com.liuning.utils.ConstantFinal;

@RequestMapping("vote")
@Controller
public class VoteController {
	
	@Autowired
	Article4VoteService avService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request) {
		List<Article4Vote> list = avService.list();
		request.setAttribute("list", list);
		return "my/vote/list";
		
	}
	
	@GetMapping("push")
	public String push(HttpServletRequest request) {
		return "my/vote/add";
		
	}
	
	@PostMapping("push")
	@ResponseBody
	public boolean  push(HttpServletRequest request,Article4Vote av) {
		//去ajax去执行发布投标的内容  去添加
		return avService.publish(av)>0;
		
	}
	
	/**
	 * 
	 * @param request
	 * @param arId  投票的id
	 * @return
	 */
	@GetMapping("getVote")
	public String getVote(HttpServletRequest request,int arId) {
		Article4Vote av = avService.findById(arId);//根据发布的投票 id去进行查询
		request.setAttribute("voteArticle", av);//查询之后存到request 作用域里面
		Gson gson = new Gson();//处理json
		
		LinkedHashMap<String,String> map = gson.fromJson(av.getContent(), LinkedHashMap.class);
		//av.getcontent是获取查询出的信息 构造方法   转化为linkedhashmap 存储到当中
		
		LinkedHashMap<String,VoteStatic> lmap = new LinkedHashMap<String,VoteStatic>();
		Set<Entry<String, String>> entrySet = map.entrySet();
		
		List<VoteStatic> voteStatics = avService.getVoteStatics(arId);//每次去计算   每个选项的票数是多少
		// 計算總共有多少人投票
		int totalNum = 0;//初始容量为0
		for (VoteStatic voteStatic : voteStatics) {
			totalNum+=voteStatic.getVoteNum();//每一次执行++
		}
		
		// 生成新的map集合存放統計數據
		for (Entry<String, String> entry : entrySet) {
			VoteStatic voteStatic = new VoteStatic();
			voteStatic.setOptionKey(entry.getKey());
			voteStatic.setOptionTitle(entry.getValue());
			voteStatic.setVoteNumTotal(totalNum);
			lmap.put(entry.getKey(), voteStatic);
			
		}
		
		
		
		//獲取統計的每一項的結果數據
		for (VoteStatic voteStatic : voteStatics) {
			VoteStatic showStatic = lmap.get(voteStatic.getOptionKey());
			showStatic.setVoteNum(voteStatic.getVoteNum());
		}
		
		request.setAttribute("lmap", lmap);
		
		return "my/vote/detail";
	}
	@RequestMapping("getCapacityByArticleIdAndUserId")
	@ResponseBody
	public boolean getCapacityByArticleIdAndUserId(Integer arId,HttpServletRequest request) {
		User loginUser = (User) request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
		Article4Vote article4Vote= avService.getCapacityByArticleIdAndUserId(arId,loginUser.getId());
		if (article4Vote!=null) {
			System.err.println("投过票了");
			return false;
		}else {
			System.err.println("123123");
			return true;
		}
	}
	
	
	@PostMapping("vote")
	@ResponseBody
	public Boolean push(HttpServletRequest request,Integer articleId,Character option) {
		User loginUser = (User)request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
		if(loginUser==null)
			return false;
		
		return avService.vote(loginUser.getId(), articleId, option)>0;
		
	}

}
