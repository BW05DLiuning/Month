<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<LINK href="css/css.css" type="text/css" rel="stylesheet">
<script src="js/jquery-1.8.2.min.js"></script>
</head>
<script type="text/javascript">
	function toDel(cid) {
		
		$.ajax({
			type:"post",
			data:{id:cid},
			url:"/article/dele",
			success:function(msg){
				  $('#center').load("/article/MyComments"); 
			}
		})
	}

	function myopen(id){
		// alert(id)
		window.open("/article/getDetail?aId="+id,"_blank");
		
	}
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
<body>
<div id="center">
	 <c:forEach items="${comlist.list}" var="comment">
		<dl>
			 <dt>文章标题：<a href="javascript:myopen(${comment.articleid})"> ${comment.articleTitle }</a></dt>
			<dt>评论内容：${comment.retext}</dt>
			<dd>发布时间:
			  <fmt:formatDate value="${comment.retime}"/>
			    <a href="javascript:toDel(${comment.id })">删除</a>
			</dd>
		</dl>
		<hr>
	</c:forEach>
		${mycomm}
	</div>
</body>
</html>