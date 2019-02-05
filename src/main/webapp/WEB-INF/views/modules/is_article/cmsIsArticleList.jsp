<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/is_article/cmsIsArticle/">信息列表</a></li>
		<shiro:hasPermission name="is_article:cmsIsArticle:edit"><li><a href="${ctx}/is_article/cmsIsArticle/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cmsIsArticle" action="${ctx}/is_article/cmsIsArticle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>栏目id：</label>
				<form:input path="categoryid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>栏目id</th>
				<shiro:hasPermission name="is_article:cmsIsArticle:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cmsIsArticle">
			<tr>
				<td><a href="${ctx}/is_article/cmsIsArticle/form?id=${cmsIsArticle.id}">
					${cmsIsArticle.id}
				</a></td>
				<td>
					${cmsIsArticle.categoryid}
				</td>
				<shiro:hasPermission name="is_article:cmsIsArticle:edit"><td>
    				<a href="${ctx}/is_article/cmsIsArticle/form?id=${cmsIsArticle.id}">修改</a>
					<a href="${ctx}/is_article/cmsIsArticle/delete?id=${cmsIsArticle.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>