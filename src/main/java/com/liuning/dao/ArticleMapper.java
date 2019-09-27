package com.liuning.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.pagehelper.PageInfo;
import com.liuning.entity.Article;
import com.liuning.entity.Comment;
import com.liuning.entity.User;
import com.liuning.entity.Yhontio;

public interface ArticleMapper {

/*	@Select("select id,title,picture,channel_id AS channelId,category_id categoryId,user_id AS userId,hits,hot,status,deleted,created,updated "
			+ " from cms_article "
			+ " where catygory_id = ${value}")
	@ResultType(Article.class)*/
	List<Article> list(@Param("channelId") Integer channelId,
			@Param("catId") Integer cid);

	Article findById(Integer articleId);
	
	int add(Article article);
	
	//传入id  在xml中写去sql语句
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

	/* int update(Article article); */
	@Select("SELECT *,channel_id channelId,category_id categoryId FROM cms_article WHERE id=#{id}")
	Article Seleartic(Article article);
	
	List<Article> hotList();

	List<Article> newarticl();
	
	@Select("select * from yhontio limit 3")
	List<Yhontio> yhontio();
	
	
	@Insert("INSERT INTO cms_comment set content=#{content}, user_id=#{userid}, article_id=#{articleid}, created=now()")
	boolean pubretext(Comment comment);
	
	
	
	//根据用户id 去查询
	@Select("SELECT cms_comment.*,cms_article.`title` AS articleTitle FROM cms_comment LEFT JOIN cms_article ON cms_comment.`articleid`=cms_article.`id` WHERE cms_comment.`userid`=#{userid}")
	List<Comment> MyComments(@Param("userid") Integer userid);
	
	
	@Delete("DELETE FROM cms_comment WHERE id=#{id} AND userid=#{userid}")
	int delecomment(@Param("userid")Integer userid,@Param("id") Integer id);
	@Update("update cms_article set hot=#{hot} , updated=now() where id=#{id}")
	int checkhot(@Param("id")Integer id,@Param("hot") Integer hot);
	
	@Select("SELECT *,cms_article.`channel_id` channelId,cms_article.`category_id` categoryId FROM cms_article WHERE id=#{id}")
	Article seleallArticle(@Param("id")int id);
	
	Article perpage(@Param("categoryId")Integer categoryId,@Param("created")String created);

	Article nextpage(@Param("categoryId")Integer categoryId,@Param("created")String created);
	@Select("SELECT *,user_id userid,article_id articleid,username FROM cms_comment LEFT JOIN cms_user ON cms_comment.`user_id`=cms_user.`id` WHERE  article_id=#{aId} ORDER BY created DESC\r\n" + 
			"")
	List<Comment> Findcomment(@Param("aId")Integer aId);
	//@Insert("INSERT INTO cms_comment set retext=#{retext}, userid=#{userid}, articleid=#{articleid}, retime=now()")
	// MyComments(Comment comment);
	@Select("SELECT COUNT(cms_comment.`id`) number FROM cms_comment WHERE article_id=#{aId}")
	int selenumber(@Param("aId")Integer aId);
	@Select("SELECT *,COUNT(cms_comment.`id`) number,cms_article.`title` title,cms_article.`id` channelid  FROM cms_comment  LEFT JOIN cms_article ON cms_comment.`article_id`=cms_article.`id`\r\n" + 
			"GROUP BY cms_comment.`article_id` ORDER BY number DESC LIMIT 10")
	List<Comment> selehotArticle();
	@Select("SELECT *,cms_article.`id` channelid FROM cms_article ORDER BY cms_article.`hits` DESC LIMIT 10")
	List<Comment> selehit();

	

}
