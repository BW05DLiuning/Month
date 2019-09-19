package com.liuning.service;

import java.util.List;

import com.liuning.entity.Article4Vote;
import com.liuning.entity.VoteStatic;

/**
 * 
 * @author Zhang旭涛
 *
 */
public interface Article4VoteService {
	
	int publish(Article4Vote av);
	
	List<Article4Vote>  list();
	
	Article4Vote  findById(Integer id);
	
	int vote(Integer userId, Integer articleId,Character option);
	
	List<VoteStatic> getVoteStatics(Integer articleId);
	
	
	

}
