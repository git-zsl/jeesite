<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/peruser/personalUser/list");
            $("#searchForm").submit();
            return false;
        }
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/personalList" method="post" class="breadcrumb form-search ">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	<ul class="ul-form">
		<li><label>姓名：</label>
			<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>

		<li><label>状态：</label>
		<form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('is_blacklist')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</li>
		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead><tr><th class="sort-column login_name">登录名</th><th class="sort-column name">姓名</th><th>电话</th><th>手机</th><%--<th>角色</th> --%><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
	<tbody>
	<c:forEach items="${page.list}" var="user">
		<tr>
			<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
			<td>${user.name}</td>
			<td>${user.phone}</td>
			<td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
			<shiro:hasPermission name="sys:user:edit"><td>
				<a href="${ctx}/peruser/personalUser/delete?id=${user.id}&delFlag=${user.delFlag ne 0?'0':'1'}" onclick="return confirmx('确认要${user.delFlag ne 0?'取消黑名单':'拉入黑名单吗？'}', this.href)">${user.delFlag ne 0?'取消黑名单':'拉入黑名单'}</a>
				<a href="${ctx}/sys/user/personalForm?id=${user.id}">修改信息</a>
			</td></shiro:hasPermission>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>