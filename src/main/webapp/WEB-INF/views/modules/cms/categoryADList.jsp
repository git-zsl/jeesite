<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>栏目管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3});
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/cms/category/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/category/">栏目列表</a></li>
		<%--<li><a href="${ctx}/cms/article/?ad=1&delFlag=2&category.id=${article.category.id}">广告列表</a></li>--%>
		<%--<shiro:hasPermission name="cms:category:edit"><li><a href="${ctx}/cms/category/form?ad=1">栏目添加</a></li></shiro:hasPermission>--%>
	</ul>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<tr><th>栏目名称</th><th>操作</th></tr>
			<c:forEach items="${list}" var="tpl">
				<tr id="${tpl.id}" pId="${tpl.parent.id ne '1'?tpl.parent.id:'0'}">
					<td><a href="${ctx}/cms/category/form?id=${tpl.id}&ad=1">${tpl.name}</a></td>
					<td>
							<a href="${ctx}/ad/adInfomation?show=1&category.id=${tpl.id}">进入管理</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>