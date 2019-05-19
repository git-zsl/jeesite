<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>背景管理</title>
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
		<li class="active"><a href="${ctx}/userbackground/userBackground/">背景列表</a></li>
		<shiro:hasPermission name="userbackground:userBackground:edit"><li><a href="${ctx}/userbackground/userBackground/form">背景添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userBackground" action="${ctx}/userbackground/userBackground/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<sys:treeselect id="user" name="user.id" value="${userBackground.user.id}" labelName="user.name" labelValue="${userBackground.user.name}"
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
				<th>用户id</th>
				<shiro:hasPermission name="userbackground:userBackground:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userBackground">
			<tr>
				<td><a href="${ctx}/userbackground/userBackground/form?id=${userBackground.id}">
					${userBackground.user.name}
				</a></td>
				<shiro:hasPermission name="userbackground:userBackground:edit"><td>
    				<a href="${ctx}/userbackground/userBackground/form?id=${userBackground.id}">修改</a>
					<a href="${ctx}/userbackground/userBackground/delete?id=${userBackground.id}" onclick="return confirmx('确认要删除该背景吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>