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
		<li class="active"><a href="${ctx}/bus/bookManager/">书籍列表</a></li>
		<shiro:hasPermission name="bus:bookManager:edit"><li><a href="${ctx}/bus/bookManager/form">书籍添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bookManager" action="${ctx}/bus/bookManager/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>书籍名称：</label>
				<form:input path="bookName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>作者：</label>
				<form:input path="author" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>一级分类：</label>
				<form:input path="firstClass" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>二级分类：</label>
				<form:input path="secondClass" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>价格：</label>
				<form:input path="price" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>书籍名称</th>
				<th>作者</th>
				<th>封面</th>
				<th>一级分类</th>
				<th>二级分类</th>
				<th>价格</th>
				<th>购买链接</th>
				<th>推荐</th>
				<th>发布时间</th>
				<shiro:hasPermission name="bus:bookManager:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bookManager">
			<tr>
				<td><a href="${ctx}/bus/bookManager/form?id=${bookManager.id}">
					${bookManager.bookName}
				</a></td>
				<td>
					${bookManager.author}
				</td>
				<td>
					${bookManager.cover}
				</td>
				<td>
					${bookManager.firstClass}
				</td>
				<td>
					${bookManager.secondClass}
				</td>
				<td>
					${bookManager.price}
				</td>
				<td>
					${bookManager.byLink}
				</td>
				<td>
					${fns:getDictLabel(bookManager.isRecommend, 'yes_no', 'yes_no')}
				</td>
				<td>
					<fmt:formatDate value="${bookManager.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="bus:bookManager:edit"><td>
    				<a href="${ctx}/bus/bookManager/form?id=${bookManager.id}">修改</a>
					<a href="${ctx}/bus/bookManager/delete?id=${bookManager.id}" onclick="return confirmx('确认要删除该书籍吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>