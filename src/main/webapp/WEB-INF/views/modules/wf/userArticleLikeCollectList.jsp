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
		<li class="active"><a href="${ctx}/wf/userArticleLikeCollect/">信息列表</a></li>
		<shiro:hasPermission name="wf:userArticleLikeCollect:edit"><li><a href="${ctx}/wf/userArticleLikeCollect/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userArticleLikeCollect" action="${ctx}/wf/userArticleLikeCollect/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<sys:treeselect id="user" name="user.id" value="${userArticleLikeCollect.user.id}" labelName="user.name" labelValue="${userArticleLikeCollect.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>文章id：</label>
				<form:input path="articleId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户id</th>
				<th>文章id</th>
				<th>点赞</th>
				<th>收藏</th>
				<shiro:hasPermission name="wf:userArticleLikeCollect:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userArticleLikeCollect">
			<tr>
				<td><a href="${ctx}/wf/userArticleLikeCollect/form?id=${userArticleLikeCollect.id}">
					${userArticleLikeCollect.user.name}
				</a></td>
				<td>
					${userArticleLikeCollect.articleId}
				</td>
				<td>
					${userArticleLikeCollect.good}
				</td>
				<td>
					${userArticleLikeCollect.collect}
				</td>
				<shiro:hasPermission name="wf:userArticleLikeCollect:edit"><td>
    				<a href="${ctx}/wf/userArticleLikeCollect/form?id=${userArticleLikeCollect.id}">修改</a>
					<a href="${ctx}/wf/userArticleLikeCollect/delete?id=${userArticleLikeCollect.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>