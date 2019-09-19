package com.liuning.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.liuning.entity.Article;

public interface ArticleMapper {

/*	@Select("select id,title,picture,channel_id AS channelId,category_id categoryId,user_id AS userId,hits,hot,status,deleted,created,updated "
			+ " from cms_article "
			+ " where catygory_id = ${value}")
	@ResultType(Article.class)*/
	List<Article> list(@Param("channelId") Integer channelId,
			@Param("catId") Integer cid);

	Article findById(Integer articleId);
	
	int add(Article article);
	
	
	List<Article> listByUser(@Param("userId") Integer userId);

	// 获取审核的文章列表
	List<Article> checkList(Integer status);

	/**
	 * 修改文章的状态
	 * @param id
	 * @param status
	 * @return
	 */
	@Update("update cms_article set status=#{status} , updated=now() where id=#{id}")
	int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
	

}
