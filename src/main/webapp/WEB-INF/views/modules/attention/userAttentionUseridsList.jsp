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
		<li class="active"><a href="${ctx}/attention/userAttentionUserids/">信息列表</a></li>
		<shiro:hasPermission name="attention:userAttentionUserids:edit"><li><a href="${ctx}/attention/userAttentionUserids/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userAttentionUserids" action="${ctx}/attention/userAttentionUserids/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<sys:treeselect id="user" name="user.id" value="${userAttentionUserids.user.id}" labelName="user.name" labelValue="${userAttentionUserids.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
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
				<th>用户id</th>
				<th>关注人ids</th>
				<shiro:hasPermission name="attention:userAttentionUserids:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userAttentionUserids">
			<tr>
				<td><a href="${ctx}/attention/userAttentionUserids/form?id=${userAttentionUserids.id}">
					${userAttentionUserids.id}
				</a></td>
				<td>
					${userAttentionUserids.user.name}
				</td>
				<td>
					${userAttentionUserids.attentionUserIds}
				</td>
				<shiro:hasPermission name="attention:userAttentionUserids:edit"><td>
    				<a href="${ctx}/attention/userAttentionUserids/form?id=${userAttentionUserids.id}">修改</a>
					<a href="${ctx}/attention/userAttentionUserids/delete?id=${userAttentionUserids.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>