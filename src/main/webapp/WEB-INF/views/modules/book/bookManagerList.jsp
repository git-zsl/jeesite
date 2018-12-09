<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>书籍管理</title>
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
		<li class="active"><a href="${ctx}/book/bookManager/">书籍列表</a></li>
		<shiro:hasPermission name="book:bookManager:edit"><li><a href="${ctx}/book/bookManager/form">书籍添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bookManager" action="${ctx}/book/bookManager/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>图书名称：</label>
				<form:input path="bookName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>作者名称：</label>
				<form:input path="author" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>图书类目：</label>
				<form:input path="firstClassId.id" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>图书分组：</label>
				<form:input path="secondClassId.id" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>图书名称</th>
				<th>作者名称</th>
				<th>图书图片存储路径</th>
				<th>图书类目</th>
				<th>图书分组</th>
				<th>更新时间</th>
				<th>点击量</th>
				<th>备注信息</th>
				<shiro:hasPermission name="book:bookManager:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bookManager">
			<tr>
				<td><a href="${ctx}/book/bookManager/form?id=${bookManager.id}">
					${bookManager.bookName}
				</a></td>
				<td>
					${bookManager.author}
				</td>
				<td>
					${bookManager.bookImagUrl}
				</td>
				<td>
					${bookManager.firstClassId.name}
				</td>
				<td>
					${bookManager.secondClassId.name}
				</td>
				<td>
					<fmt:formatDate value="${bookManager.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${bookManager.hits}
				</td>
				<td>
					${bookManager.remarks}
				</td>
				<shiro:hasPermission name="book:bookManager:edit"><td>
    				<a href="${ctx}/book/bookManager/form?id=${bookManager.id}">修改</a>
					<a href="${ctx}/book/bookManager/delete?id=${bookManager.id}" onclick="return confirmx('确认要删除该书籍吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>