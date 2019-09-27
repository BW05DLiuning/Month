package com.liuning.test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.liuning.common.utils.FileUtil;
import com.liuning.entity.Article;
import com.liuning.service.ArticleService;

public class TestAddArticel extends TestNote{//继承一个类   方便写注解
		
	@Autowired
	ArticleService  as;
	@Test
	public void Nsumber() {//验证随机数字
		Random random = new Random();
		System.out.println(random.nextInt(2));
	}
		@Test
		public void TestImp() throws IOException {
			int channelId[]={1,2,3,4,5,6,7,8};
			List<String> filelist = FileUtil.getFileList("D:\\CMS月考文章");
			Random random = new Random();
			for (String string : filelist) {
				Article article = new Article();
				String content;//创建空字符串  代表文章内容
				content=FileUtil.readFile(string);//读取的内容放入字符串  string是文件夹下面的文件
				article.setContent(content);//给文章赋值
				article.setTitle(string.substring(string.lastIndexOf('\\')+1,string.lastIndexOf('.')));//截取文件名称 \\最后一次出现的位置包含前面  而后面不包括
				article.setHits(10+random.nextInt(90));//设置点击量10-100
				article.setHot(random.nextInt(2));//设置是否为热门文章 随机数
				article.setUserId(35);//用户
				article.setChannelId(channelId[random.nextInt(7)]);//随机频道
				as.add(article);//调用添加的方法
			}
		
		}
	
		
}
