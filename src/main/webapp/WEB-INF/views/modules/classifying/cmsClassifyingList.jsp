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
		<li class="active"><a href="${ctx}/classifying/cmsClassifying/">分类列表</a></li>
		<shiro:hasPermission name="classifying:cmsClassifying:edit"><li><a href="${ctx}/classifying/cmsClassifying/form">分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cmsClassifying" action="${ctx}/classifying/cmsClassifying/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分类：</label>
				<form:input path="classifying" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>分类</th>
				<shiro:hasPermission name="classifying:cmsClassifying:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cmsClassifying">
			<tr>
				<td><a href="${ctx}/classifying/cmsClassifying/form?id=${cmsClassifying.id}">
					${cmsClassifying.id}
				</a></td>
				<td>
					${cmsClassifying.classifying}
				</td>
				<shiro:hasPermission name="classifying:cmsClassifying:edit"><td>
    				<a href="${ctx}/classifying/cmsClassifying/form?id=${cmsClassifying.id}">修改</a>
					<a href="${ctx}/classifying/cmsClassifying/delete?id=${cmsClassifying.id}" onclick="return confirmx('确认要删除该分类吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>