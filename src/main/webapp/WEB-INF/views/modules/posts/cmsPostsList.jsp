<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分类管理</title>
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
		<li class="active"><a href="${ctx}/posts/cmsPosts/">分类列表</a></li>
		<shiro:hasPermission name="posts:cmsPosts:edit"><li><a href="${ctx}/posts/cmsPosts/form">分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cmsPosts" action="${ctx}/posts/cmsPosts/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>岗位：</label>
				<form:select path="id" class="input-medium">
					<form:option value="" label="--请选择--"/>
					<form:options items="${postsList}" itemLabel="posts" itemValue="id" htmlEscape="false" />
				</form:select>
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
				<th>岗位</th>
				<shiro:hasPermission name="posts:cmsPosts:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cmsPosts">
			<tr>
				<td><a href="${ctx}/posts/cmsPosts/form?id=${cmsPosts.id}">
					${cmsPosts.id}
				</a></td>
				<td>
					${cmsPosts.posts}
				</td>
				<shiro:hasPermission name="posts:cmsPosts:edit"><td>
    				<a href="${ctx}/posts/cmsPosts/form?id=${cmsPosts.id}">修改</a>
					<a href="${ctx}/posts/cmsPosts/delete?id=${cmsPosts.id}" onclick="return confirmx('确认要删除该分类吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>