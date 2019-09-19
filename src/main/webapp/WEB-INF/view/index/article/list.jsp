<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="nav">

  
	<!-- 所有分类下的文章 -->
	<div id="article">

	<ul class="list-unstyled">
		<hr>
	   <!-- 栏目下所有文章 -->
		<c:forEach items="${pageInfo.list }" var="a">
			<li class="media"><img  width="120px" height="120px" class="mr-3" src="/pic/${a.picture }"
				alt="no pic">
				<div class="media-body">
					<h5 class="mt-0 mb-1"><small><a href="javascript:myopen(${a.id })"> ${a.title }</a></small></h5>
					<br>
					<br>
					<h5 class="mt-0 mb-1"><small> ${a.username } &nbsp;  ${a.created }</small></h5>
					
				</div>
				
				
				</li>
				<hr>
		</c:forEach>
	</ul>
</ul>
	</div>

	<script type="text/javascript">
	 function myopen(id){
		 //在新窗口打开文章的详情J
		 window.open("/article/getDetail?aId="+id,"_blank")
	 }
	</script>
<script type="text/javascript" src="/resource/js/cms.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>