<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">

function myopen(id){
	// alert(id)
	window.open("/article/getDetail?aId="+id,"_blank");
	
}
function update(id) {
	$('#center').load("/article/update?id="+id);
}
</script>
</head>
<body>
<!--查询出来的数据 分页进行循环输出  -->
	<c:forEach items="${myarticles.list}" var="article">
		<dl>
			<dt><a href="javascript:myopen(${article.id })">${article.title }</a></dt>
			<dd>作者:${sessionScope.USER_SESSION_KEY.username} 发布时间:${article.created}
				频道:${article.chnName}  分类:${article.catName}
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 
				<a href="javascript:update(${article.id })" style="color: red;">修改</a>
			</dd>
			
		</dl>
		<hr>
	</c:forEach>
	<!--分页的工具类实现的  -->
	${pageStr}


</body>
<script type="text/javascript">
	$(function(){
	    $('.page-link').click(function (e) {
	    	
	    	
	    	  //获取点击的的url
	        var url = $(this).attr('data');
	        /* alert(url); */
	    
	       //在中间区域显示地址的内容
	       $('#center').load(url);
	    });
	})
</script>
</html>