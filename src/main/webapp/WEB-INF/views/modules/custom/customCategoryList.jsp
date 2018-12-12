<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义栏目配置管理</title>
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
		<li class="active"><a href="${ctx}/custom/customCategory/">自定义栏目配置列表</a></li>
		<shiro:hasPermission name="custom:customCategory:edit"><li><a href="${ctx}/custom/customCategory/form">自定义栏目配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customCategory" action="${ctx}/custom/customCategory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>自定义栏目名称：</label>
				<form:input path="categoryName" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>自定义栏目名称</th>
				<th>自定义栏目编号</th>
				<th>栏目标志</th>
				<shiro:hasPermission name="custom:customCategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customCategory">
			<tr>
				<td><a href="${ctx}/custom/customCategory/form?id=${customCategory.id}">
					${customCategory.id}
				</a></td>
				<td>
					${customCategory.categoryName}
				</td>
				<td>
					${customCategory.categoryId}
				</td>
				<td>
					${fns:getDictLabel(customCategory.customMark, 'custom_category', '')}
				</td>
				<shiro:hasPermission name="custom:customCategory:edit"><td>
    				<a href="${ctx}/custom/customCategory/form?id=${customCategory.id}">修改</a>
					<a href="${ctx}/custom/customCategory/delete?id=${customCategory.id}" onclick="return confirmx('确认要删除该自定义栏目配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>