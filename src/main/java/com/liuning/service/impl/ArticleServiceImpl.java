package com.liuning.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuning.dao.ArticleMapper;
import com.liuning.entity.Article;
import com.liuning.entity.Comment;
import com.liuning.entity.User;
import com.liuning.entity.Yhontio;
import com.liuning.service.ArticleService;


@Service
public class ArticleServiceImpl implements ArticleService{
	
	@Autowired
	ArticleMapper articleMapper;

	@Override
	public int post(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.add(article);
	}

	@Override
	public PageInfo<Article> list(Integer pageNum,Integer channelId, Integer cid) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum,2);
		
		List<Article> articles =articleMapper.list(channelId,cid);
		
		return new PageInfo<Article>(articles);
	}

	@Override
	public Article findById(Integer articleId) {
		// TODO Auto-generated method stub
		return articleMapper.findById(articleId);
		
	}

	/*
	 * @Override public int update(Article article) { // TODO Auto-generated method
	 * stub return articleMapper.update(article); }
	 */

	@Override
	public int logicDelete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int logicDeleteBatch(Integer[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int add(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.add(article);
	}

	@Override
	public PageInfo<Article> getByUserId(Integer id, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);//查询出来的数据进行分页
		PageInfo<Article> pageInfo = new PageInfo<Article>(articleMapper.listByUser(id));
		
		return pageInfo;
	}

	@Override
	public PageInfo<Article> checkList(Integer status, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNumber, pageSize);
		List<Article> articles=  articleMapper.checkList(status);
		
		return new PageInfo<Article>(articles);
	}

	@Override
	public int check(Integer id, Integer status) {
		// TODO Auto-generated method stub
		return articleMapper.updateStatus(id,status);
	}

	@Override
	public Article Seleartic(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.Seleartic(article);
	}

	@Override
	public PageInfo<Article> listhots(Integer pageSize, Integer pageNum) {
			//查询热门文章
		PageHelper.startPage(pageNum, pageSize);
		
		List<Article> list=  articleMapper.hotList();
		for (Article article : list) {
			System.err.println(article.toString());
		}
		return new PageInfo<Article>(list);
	}

	@Override
	public List<Article> newarticl() {
		// TODO Auto-generated method stub
		return articleMapper.newarticl();
	}

	@Override
	public List<Yhontio> yhontio() {
		// TODO Auto-generated method stub
		return articleMapper.yhontio();
	}

	@Override
	public boolean pubretext(Comment comment) {
		// TODO Auto-generated method stub
		return articleMapper.pubretext(comment);
	}

	@Override
	public PageInfo<Comment> MyComments(Integer userid,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<Comment> list = articleMapper.MyComments(userid);
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(list);
		return pageInfo;
	}

	@Override
	public int delecomment(Integer userid, Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.delecomment(userid,id);
	}

	@Override
	public int checkhot(Integer id, Integer hot) {
		// TODO Auto-generated method stub
		return articleMapper.checkhot(id,hot);
	}

	@Override
	public Article seleallArticle(int id) {
		// TODO Auto-generated method stub
		return articleMapper.seleallArticle(id);
	}

	@Override
	public Article perpage(Integer categoryId,String created) {
		// TODO Auto-generated method stub
		return articleMapper.perpage(categoryId,created);
	}
	@Override
	public Article nextpage(Integer categoryId, String created2) {
		// TODO Auto-generated method stub
		return articleMapper.nextpage(categoryId,created2);
	}
	@Override
	public PageInfo<Comment> Findcomment(Integer aId, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<Comment> list = articleMapper.Findcomment(aId);
		
		return new PageInfo<Comment>(list);
	}
	
	@Override
	public int selenumber(Integer aId) {
		// TODO Auto-generated method stub
		return articleMapper.selenumber(aId);
	}
	
	@Override
	public List<Comment> selehotArticle() {
		// TODO Auto-generated method stub
		return articleMapper.selehotArticle();
	}
	@Override
	public List<Comment> selehit() {
		// TODO Auto-generated method stub
		return articleMapper.selehit();
	}
}
