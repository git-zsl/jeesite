<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公告管理</title>
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
		<li class="active"><a href="${ctx}/notice/bookNotice/">公告列表</a></li>
		<shiro:hasPermission name="notice:bookNotice:edit"><li><a href="${ctx}/notice/bookNotice/form">公告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bookNotice" action="${ctx}/notice/bookNotice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建者：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公告内容</th>
				<th>跳转链接</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="notice:bookNotice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bookNotice">
			<tr>
				<td><a href="${ctx}/notice/bookNotice/form?id=${bookNotice.id}">
					${bookNotice.content}
				</a></td>
				<td>
					${bookNotice.href}
				</td>
				<td>
					${bookNotice.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${bookNotice.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${bookNotice.remarks}
				</td>
				<shiro:hasPermission name="notice:bookNotice:edit"><td>
    				<a href="${ctx}/notice/bookNotice/form?id=${bookNotice.id}">修改</a>
					<a href="${ctx}/notice/bookNotice/delete?id=${bookNotice.id}" onclick="return confirmx('确认要删除该公告吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>