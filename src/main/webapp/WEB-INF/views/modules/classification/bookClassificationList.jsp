<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>书籍分类管理</title>
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
		<li class="active"><a href="${ctx}/classification/bookClassification/">书籍分类列表</a></li>
		<shiro:hasPermission name="classification:bookClassification:edit"><li><a href="${ctx}/classification/bookClassification/form">书籍分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bookClassification" action="${ctx}/classification/bookClassification/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分类：</label>
				<form:input path="classification" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分类</th>
				<shiro:hasPermission name="classification:bookClassification:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bookClassification">
			<tr>
				<td><a href="${ctx}/classification/bookClassification/form?id=${bookClassification.id}">
					${bookClassification.classification}
				</a></td>
				<shiro:hasPermission name="classification:bookClassification:edit"><td>
    				<a href="${ctx}/classification/bookClassification/form?id=${bookClassification.id}">修改</a>
					<a href="${ctx}/classification/bookClassification/delete?id=${bookClassification.id}" onclick="return confirmx('确认要删除该书籍分类吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>