<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/resource/css/bootstrap.min.css">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
</head>
<body>
	<div class="container">

		<dl>
			<dt>${article.title }</dt>
			<!--这里是文章的标题以及内容  -->
			<hr>

			<dd>${article.content}</dd>
		</dl>
		
		
		<div id="comm">
		<c:forEach items="${com.list}" var="ss">
			<h1 id="c">${ss.content }----
				<fmt:formatDate value="${ss.created }" />
				---${ss.username}
			</h1>
		</c:forEach>
		</div>

		${commentPageIfor}
	</div>
	<div align="center">
	<dt >评论数量:${selenumber}</dt>
	</div>
	

	<div class="input-group">
		<div align="center" class="container">
			
			<input type="text" class="container" name="retext"> 
			<span class="input-group-addon"><input type="submit" value="提交评论" onclick="pl(${article.id})"></span>
			<!--提交评论  更具文章id和用户id 和 评论内容去给评论表增加一条数据  -->
		</div>
	</div>
	
	
		<dd>
			<div id="commentList"></div>
		</dd>
	<br>
		<div align="center">
			<input type="button" value="上一页" onclick="pre(${article.id})">
			 
			 <input type="button" value="下一页" onclick="next(${article.id})"> 
		
		</div>
		<div>
			
		</div>
		
		<div class="card" align="center">
			   <div class="card-header">热门评论</div>
			      <ol>
			   			<c:forEach items="${selehot}" var="ss">
			   				<li class="text-truncate"><a href="/article/getDetail?aId=${ss.channelid}">${ss.title}</a></li>
			   			</c:forEach>
			     </ol>
			   </div>
			   
			   <div class="card" align="center">
			   <div class="card-header">点击热门</div>
			      <ol>
			   			<c:forEach items="${selehitlist}" var="ll">
			   				<li class="text-truncate"><a href="/article/getDetail?aId=${ll.channelid}">${ll.title}</a></li>
			   			</c:forEach>
			     </ol>
			   </div>
	<script type="text/javascript">
	function pl(id) {
		var comment=$("[name='retext']").val()
		$.ajax({
			type:"post",
			data:{articleid:id,content:comment},
			url:"/article/MyComment",
			success:function(msg){
				if(msg>0){
					alert("成功")
					$("#commentList").load("/article/getDetail?aId=${article.id}"); 
					history.go(0)
					
				}else{
					alert("失败")
				}
			}
		})
	}
	function next(id) {
		alert(id)
		$.ajax({
			type:"post",
			data:{id:id},
			url:"/article/nextpage",
			success:function(msg){
				if(msg.id!=undefined){
					location.href="/article/getDetail?aId="+msg.id
				}else{
					alert("已经是最后一页了")
				}
			}
		})
	}
		function pre(id) {
			alert(id)
			$.ajax({
				type:"post",
				data:{id:id},
				url:"/article/perpage",
				success:function(msg){
					if(msg.id!=undefined){
						location.href="/article/getDetail?aId="+msg.id
					}else{
						alert("已经是最前一页了")
					}
				}
			})
		}
		
		 $('.page-link').click(function (e) {
	        	
       	  //获取点击的的url
           var url = $(this).attr('data');
          // console.log(url);
          //在中间区域显示地址的内容
           $('#comm').load(url);
       });
</script>
</body>
</html>