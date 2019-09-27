package com.liuning.service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.liuning.entity.Article;
import com.liuning.entity.Comment;
import com.liuning.entity.User;
import com.liuning.entity.Yhontio;

/**
 * 
 * @author liuning
 *
 */
public interface ArticleService {
	
	int post(Article article);
	
	/**
	 * 分页查询后某类的文章 
	 * @param pageNum
	 * @param cid
	 * @param status 
	 * @return
	 */
	PageInfo<Article> list(Integer pageNum, Integer channelId,Integer cid);
	
//	PageInfo<Article> listByChannel(Integer pageNum,Integer cid);
	
	Article findById(Integer ArticleId);
	
	/* int update(Article article); */
	
	/**
	 * 删除 逻辑删除
	 * @param article
	 * @return
	 */
	int logicDelete(Integer id);
	
	/**
	 * 批量的逻辑删除
	 * @param ids
	 * @return
	 */
	int logicDeleteBatch(Integer[] ids);

	int add(Article article);
	
	PageInfo<Article> getByUserId(Integer id, int pageNum, int pageSize);

	PageInfo<Article> checkList(Integer status, int pageNumber, int pageSize);

	/**
	 * 审核文章   
	 * @param id  文章id
	 * @param status  期望文章修改后的状态
	 * @return  修改数据的条数
	 */
	int check(Integer id, Integer status);

	Article Seleartic(Article article);
	//查询热门文章
	PageInfo<Article> listhots(Integer pageSize, Integer pageNum);

	List<Article> newarticl();

	List<Yhontio> yhontio();

	boolean pubretext(Comment comment);

	

	PageInfo<Comment> MyComments(Integer userid, int pageNum, int pageSize);

	int  delecomment(Integer userid, Integer id);

	int checkhot(Integer id, Integer hot);

	Article seleallArticle(int id);

	Article perpage(Integer categoryId, String created);

	Article nextpage(Integer categoryId, String created2);

	PageInfo<Comment> Findcomment(Integer aId, Integer pageSize, Integer pageNum);

	int selenumber(Integer aId);

	List<Comment> selehotArticle();

	List<Comment> selehit();

	 //MyComments(Comment comment);

	

	

	

	

	
	
	
	
}
